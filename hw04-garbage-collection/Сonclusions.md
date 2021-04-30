# Сравнение разных сборщиков мусора

## Задача:
Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old) и время которое ушло на сборки в минуту.

Добиться OutOfMemory в этом приложении через медленное подтекание по памяти (например добавлять элементы в List и удалять только половину).

Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало с OOM примерно через 5 минут после начала работы.

Собрать статистику (количество сборок, время на сборки) по разным GC.

Какой gc лучше и почему?

#### Environment:
Linux Fedora 33  
Java-11-openjdk  
VisualVM 2.0.7

#### VM options:  
-Xms128m  
-Xmx128m  
-Xlog:gc=debug:file=./hw04-garbage-collection/src/main/java/ru/amlet/logs/<some>GC-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m  
-XX:+HeapDumpOnOutOfMemoryError  
-XX:HeapDumpPath=./hw04-garbage-collection/src/main/java/ru/amlet/logs/dump  
-XX:+UnlockExperimentalVMOptions  
-XX:+Use<some>GC

## Выводы:

| Garbage Collector | Время работы app | Conclusions  | Logs | Screens |
|:-----------------:|:----------------:|:------------:|:----:|:-------:|
| Serial | 153c | Остановка приложения для сборки, долгие паузы | [link](./src/main/java/ru/amlet/logs/Serial-131698-2021-04-30_09-44-24.log) | [link](./src/main/java/ru/amlet/logs/Serial.png) |
| Parallel | 34c | Остановка приложения для сборки, короткие паузы, но требует больше ресурсов | [link](./src/main/java/ru/amlet/logs/Parallel-131101-2021-04-30_09-41-04.log) | [link](./src/main/java/ru/amlet/logs/Parallel.png) |
| CMS | 134c | Разная работа со старым и новым поколениями, но Deprecated т.к. уступает G1 | [link](./src/main/java/ru/amlet/logs/CMS-130260-2021-04-30_09-34-57.log) | [link](./src/main/java/ru/amlet/logs/CMS.png) |
|  G1  | \>999c | Гибкая работа с памятью позволяет проводить сборку максимально эффективно | [link](./src/main/java/ru/amlet/logs/G1-132468-2021-04-30_09-49-06.log) | [link](./src/main/java/ru/amlet/logs/G1.png) |
| Z | 4c | Отсутствие сборок, отсутствие пауз, требуется большой heap | [link](./src/main/java/ru/amlet/logs/Z-129440-2021-04-30_09-30-04.log) | [link](./src/main/java/ru/amlet/logs/Z.png) |
| Shenandoah | 78c | Короткие паузы на сборку, но большие накладные расходы | [link](./src/main/java/ru/amlet/logs/Shenandoah-128321-2021-04-30_09-22-49.log) | [link](./src/main/java/ru/amlet/logs/Shenandoah.png) |
