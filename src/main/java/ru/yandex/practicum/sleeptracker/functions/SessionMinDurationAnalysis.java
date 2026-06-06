package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TreeMap;

public class SessionMinDurationAnalysis implements SleepingAnalysis<Integer> {
    private static final String ANALYSIS_DESCRIPTION = "Минимальная продолжительность сессии (в минутах)";

    @Override
    public SleepAnalysisResult<Integer> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        Optional<Integer> result = sessions.values().stream()
                .map(SleepingSession::getDurationInMinutes)
                .min(Integer::compare);

        return result.map(integer ->
                        new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION,
                                integer))
                .orElseGet(() ->
                        new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION));
    }
}
