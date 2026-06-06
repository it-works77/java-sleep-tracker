package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class SessionNumberAnalysis implements SleepingAnalysis<Integer> {
    private static final String ANALYSIS_DESCRIPTION = "Количество сессий сна";

    @Override
    public SleepAnalysisResult<Integer> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION, sessions.size());
    }
}
