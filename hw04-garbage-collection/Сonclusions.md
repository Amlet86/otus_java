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

## Результат:

| Garbage Collector | Время работы app | Среднее время работы GC | Количество вызовов GC | Итого STW | % STW от времени работы app | Logs | Screens |
|:-----------------:|:----------------:|:-----------------------:|:---------------------:|:---------:|:---------------------------:|:----:|:-------:|
| Serial | 120s | 35ms | 1300 | 47s | 40 | [link](./src/main/java/ru/amlet/logs/Serial-131698-2021-04-30_09-44-24.log) | [link](./src/main/java/ru/amlet/logs/Serial.png) |
| Parallel | 34s | 33ms | 600 | 20s | 60 | [link](./src/main/java/ru/amlet/logs/Parallel-131101-2021-04-30_09-41-04.log) | [link](./src/main/java/ru/amlet/logs/Parallel.png) |
| CMS | 140s | 35ms | 2000 | 70s | 50 | [link](./src/main/java/ru/amlet/logs/CMS-130260-2021-04-30_09-34-57.log) | [link](./src/main/java/ru/amlet/logs/CMS.png) |
|  G1  | 600s | 9ms | 27000 | 240s | 40 | [link](./src/main/java/ru/amlet/logs/G1-132468-2021-04-30_09-49-06.log) | [link](./src/main/java/ru/amlet/logs/G1.png) |
| Z | 4s | 10ms | 60 | 0.6s | 15 | [link](./src/main/java/ru/amlet/logs/Z-129440-2021-04-30_09-30-04.log) | [link](./src/main/java/ru/amlet/logs/Z.png) |
| Shenandoah | 75s | 5ms | 7500 | 41s | 55 | [link](./src/main/java/ru/amlet/logs/Shenandoah-128321-2021-04-30_09-22-49.log) | [link](./src/main/java/ru/amlet/logs/Shenandoah.png) |

## Выводы:
**ZGC** может быть интересен для работы приложений использующих очень большой heap в N TByte и требующих маленькие простои для сборки мусора, до 10ms. 
В тестовом окружении показал себя самым не подходящим ввиду самого быстрого OOM.  
**Parallel** подойдёт для приложений работающих на многоядерной платформе с адекватным размером heap. 
Для сборки мусора приложение останавливается, но паузы короткие т.к. запускает несколько потоков для сборки. 
В тестовом окружении показал себя не подходящим из-за быстрого OOM и при этом весьма большого процента процессорного времени на сборку.  
**Shenandoah** короткие паузы на сборку, но большие накладные расходы. 
Разрабатывает компания Red Hat, а Oracle отказалась поддерживать Shenandoah, поэтому в OracleJDK его нет. 
Подойдёт для приложений, где нежелательны длинные задержки. 
В тестовом приложении не лучший вариант из-за простоя 55% процессорного времени.  
**Serial** при любом типе сборки коллектор останавливает потоки основного приложения. Один поток коллектора выполняет все этапы. 
При текущих параметрах тестового окружения показал себя достаточно хорошо, всего 40% времени было занято сборкой, но общее время до ООМ меньше чем у конкурентов.  
**CMS** разная работа со старым и новым поколениями. 
Чистка нового поколения как в Parallel GC: основное приложение останавливается, и запускаются несколько потоков коллектора.
️Старшее поколение собирается по-другому: большая часть графа объектов строится параллельно с основным приложением, объекты удаляются тоже параллельно, поэтому сборщик и называется Concurrent Mark Sweep - параллельная разметка и удаление.
Помечен Deprecated в Java 9, потому что уступает G1, что подтверждается работой тестового приложения.  
**G1** суть Garbage First — деление памяти на 2048 регионов, которым присваивается тип - свободный, young, survivor или old.
Гибкая работа с памятью позволяет проводить сборку максимально эффективно. 
При выбранных для тестирования опциях **_подошел лучше других_** благодаря максимальному времени работы приложения и всего при 40% STW.
