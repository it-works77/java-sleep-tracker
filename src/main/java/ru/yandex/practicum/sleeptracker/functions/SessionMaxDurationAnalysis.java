package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TreeMap;

public class SessionMaxDurationAnalysis implements SleepingAnalysis {
    @Override
    public SleepAnalysisResult<?> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        Optional<Integer> maxDuration = sessions.values().stream()
                .map(SleepingSession::getDurationInMinutes)
                .max(Integer::compare);

        return maxDuration.map(integer ->
                        new SleepAnalysisResult<>("Максимальная продолжительность сессии (в минутах):",
                                integer))
                .orElseGet(() ->
                        new SleepAnalysisResult<>("Максимальная продолжительность сессии (в минутах):"));
    }
}
