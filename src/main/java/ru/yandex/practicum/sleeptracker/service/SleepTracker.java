package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.functions.SleepingAnalysis;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            br.lines()
                    .forEach(line -> {
                                String[] lineTokens = line.split(";");

                                if (lineTokens.length == LOG_TOKENS_NUMBER) {
                                    SleepingSession ss = null;
                                    try {
                                        ss = new SleepingSession(lineTokens[0],
                                                lineTokens[1],
                                                lineTokens[2],
                                                LOG_DATETIME);
                                    } catch (DateTimeParseException ex) {
                                        System.out.println("Пропускаем строку. " +
                                                "Неверный формат даты и времени: " +
                                                lineTokens[0] + ", " +
                                                lineTokens[1]);
                                        return;
                                    } catch (IllegalArgumentException ex) {
                                        System.out.println("Пропускаем строку. " +
                                                "Неверное качество сна: " +
                                                lineTokens[2]);
                                        return;
                                    }

                                    // Session end have to be greater than start
                                    if (ss.getSessionEnd().isBefore(ss.getSessionStart())
                                            || ss.getSessionEnd().isEqual(ss.getSessionStart())) {
                                        System.out.println("Пропускаем строку. " +
                                                "Время окончания сессии " +
                                                ss.getSessionEnd() +
                                                " должно быть больше начала " +
                                                ss.getSessionStart());
                                    } else {
                                        // Is session start already exits?
                                        if (sleepingSessionJournal.containsKey(ss.getSessionStart())) {
                                            System.out.println("Пропускаем строку. " +
                                                    "Уже есть сессия с таким временем начала " +
                                                    ss.getSessionStart());
                                        } else {
                                            sleepingSessionJournal.put(ss.getSessionStart(), ss);
                                        }
                                    }
                                } else {
                                    System.out.println("Пропускаем строку. " +
                                            "Неверная структура лога: количество частей строки не равно " +
                                            LOG_TOKENS_NUMBER);
                                }
                            }
                    );
        } catch (InvalidPathException ex) {
            System.out.println("Неверный путь к файлу" + logFilePath + ". " + ex.getMessage());
            System.exit(1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        // At least one session
        if (sleepingSessionJournal.isEmpty()) {
            System.out.println("Нет записей в журнале сессий" + logFilePath);
            System.exit(1);
        }
    }

    public List<SleepAnalysisResult<?>> getAnalytics() {
        return analyticFunctions.stream()
                .map(func -> func.get(sleepingSessionJournal))
                .collect(Collectors.toList());
    }
}
