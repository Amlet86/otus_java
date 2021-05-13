package ru.amlet;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

import com.sun.management.GarbageCollectionNotificationInfo;

public class Monitoring {

    static void logMXBeanName(){
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
    }

    static void switchOnMonitoring() {
        AtomicInteger i = new AtomicInteger(1);
        AtomicLong allDuration = new AtomicLong();
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    allDuration.addAndGet(duration);
                    long avDuration =  allDuration.longValue() / i.longValue();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms) " + i.incrementAndGet() + "times " + avDuration + "av_ms " + allDuration + " fullTimeSTW" );

                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    static void mBeanRegister(Object mbean) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=ru.amlet.Benchmark");

        mbs.registerMBean(mbean, name);
    }

}
