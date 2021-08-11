package ru.amlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPong {

    private static final Logger logger = LoggerFactory.getLogger(PingPong.class);
    private String last = "2";

    public static void main(String[] args) {
        PingPong pingPong = new PingPong();
        new Thread(() -> pingPong.action("1")).start();
        new Thread(() -> pingPong.action("2")).start();
    }

    private synchronized void action(String message) {
        try {

            for (int i = 1; i < 10; i++) {
                while (last.equals(message)) {
                    this.wait();
                }
                notifyAndPrint(message, i);
            }

            for (int i = 10; i > 0; i--) {
                while (last.equals(message)) {
                    this.wait();
                }
                notifyAndPrint(message, i);
            }

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new NotInterestingException(ex);
        }
    }

    private void notifyAndPrint(String message, int count) {
        last = message;
        notifyAll();
        logger.info(Thread.currentThread().getName() + ": " + count);
    }

    private static class NotInterestingException extends RuntimeException {
        NotInterestingException(InterruptedException ex) {
            super(ex);
        }
    }
}
