package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.service.SleepTracker;

import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {

        // Приложение должно принимать на вход как аргумент командной строки путь к файлу с логом сна
        if (args.length != 1) {
            System.out.println("Завершение работы. " +
                    "Необходимо указать как аргумент командной строки путь к файлу с логом сна");
            System.out.println("Например:\njava " + SleepTrackerApp.class.getSimpleName() +
                    " ./src/main/resources/sleep_log.txt");
            return;
        }
        String sleepLogFilePath = args[0];

        ArrayList<SleepingAnalysis<?>> analyticFunctions = new ArrayList<>();
        analyticFunctions.add(new SessionNumberAnalysis());
        analyticFunctions.add(new SessionMaxDurationAnalysis());
        analyticFunctions.add(new SessionMinDurationAnalysis());
        analyticFunctions.add(new SessionAvgDurationAnalysis());
        analyticFunctions.add(new SessionBadQualityCounterAnalysis());
        analyticFunctions.add(new UserChronotypeAnalysis());
        analyticFunctions.add(new SessionSleeplessNightsAnalysis());

        SleepTracker app = new SleepTracker(sleepLogFilePath, analyticFunctions);
        app.init();

        /* Функции должны запускаться в методе main, а результат их выполнения должен выводиться на экран.
        Весь код вывода должен располагаться в методе main.*/
        List<SleepAnalysisResult<?>> results = app.getAnalytics();
        results.stream().forEach(System.out::println);
    }
}