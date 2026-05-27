package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.functions.SleepingAnalysis;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SleepTracker {
    private static final int LOG_TOKENS_NUMBER = 3;
    private static final DateTimeFormatter LOG_DATETIME = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private final String sleepLogFile;
    private final TreeMap<LocalDateTime, SleepingSession> sleepingSessionJournal;
    private final ArrayList<SleepingAnalysis> analyticFunctions;

    public SleepTracker(String sleepLogFile, ArrayList<SleepingAnalysis> analyticFunctions) {
        this.sleepLogFile = sleepLogFile;
        sleepingSessionJournal = new TreeMap<>();
        this.analyticFunctions = analyticFunctions;
    }

    public void init() {
        Path logFilePath = Paths.get(sleepLogFile);

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath.toFile()))) {
            String line;

            // TODO Don't use for/while loop
            while ((line = br.readLine()) != null) {
                String[] lineTokens = line.split(";");

                if (lineTokens.length == LOG_TOKENS_NUMBER) {
                    // TODO Make constructor from whole line
                    SleepingSession ss = new SleepingSession(lineTokens[0], lineTokens[1], lineTokens[2], LOG_DATETIME);
                    // TODO Check: is session start already exits?
                    sleepingSessionJournal.put(ss.getSessionStart(), ss);
                } else {
                    System.out.println("Неверная структура лога: количество частей строки не равно" + LOG_TOKENS_NUMBER);
                    continue;
                }
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<SleepAnalysisResult<?>> getAnalytics() {
//        TODO run functions here
        return analyticFunctions.stream()
                .map(func -> func.get(sleepingSessionJournal))
                .collect(Collectors.toList());
    }
}
