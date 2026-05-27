package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.ChronotypeAnalysis;
import ru.yandex.practicum.sleeptracker.functions.SessionNumberAnalysis;
import ru.yandex.practicum.sleeptracker.functions.SleepingAnalysis;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.service.SleepTracker;

import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {
    public static final String SLEEP_LOG_FILEPATH = "./src/main/resources/sleep_log.txt";


    public static void main(String[] args) {

        ArrayList<SleepingAnalysis> analyticFunctions = new ArrayList<>();
        analyticFunctions.add(new SessionNumberAnalysis());
        analyticFunctions.add(new ChronotypeAnalysis());

        SleepTracker app = new SleepTracker(SLEEP_LOG_FILEPATH, analyticFunctions);
        app.init();

        /* Функции должны запускаться в методе main, а результат их выполнения должен выводиться на экран.
        Весь код вывода должен располагаться в методе main.*/
        List<SleepAnalysisResult<?>> results = app.getAnalytics();
        results.stream().forEach(System.out::println);
    }
}