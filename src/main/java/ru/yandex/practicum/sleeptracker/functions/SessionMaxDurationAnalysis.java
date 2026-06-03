package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TreeMap;

public class SessionMaxDurationAnalysis implements SleepingAnalysis {
    private static final String ANALYSIS_DESCRIPTION = "Максимальная продолжительность сессии (в минутах)";

    @Override
    public SleepAnalysisResult<Integer> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        Optional<Integer> result = sessions.values().stream()
                .map(SleepingSession::getDurationInMinutes)
                .max(Integer::compare);

        return result.map(integer ->
                        new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION,
                                integer))
                .orElseGet(() ->
                        new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION));
    }
}
