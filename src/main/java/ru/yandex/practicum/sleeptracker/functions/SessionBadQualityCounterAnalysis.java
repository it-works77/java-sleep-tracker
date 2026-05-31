package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class SessionBadQualityCounterAnalysis implements SleepingAnalysis {
    private static final String ANALYSIS_DESCRIPTION = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult<?> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        long result = sessions.values().stream()
                .filter(s -> s.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION, result);
    }
}
