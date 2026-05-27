package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.TreeMap;

@FunctionalInterface
public interface SleepingAnalysis {
    SleepAnalysisResult<?> get(TreeMap<LocalDateTime, SleepingSession> sessions);
}
