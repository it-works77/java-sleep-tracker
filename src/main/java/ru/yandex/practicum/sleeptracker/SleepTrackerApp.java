package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.service.SleepTracker;

public class SleepTrackerApp {
    public static final String SLEEP_LOG_FILEPATH = "./src/main/resources/sleep_log.txt";


    public static void main(String[] args) {

        SleepTracker app = new SleepTracker(SLEEP_LOG_FILEPATH);
        app.init();
        app.run();



        /* Функции должны запускаться в методе main, а результат их выполнения должен выводиться на экран.
        Весь код вывода должен располагаться в методе main.*/
    }
}