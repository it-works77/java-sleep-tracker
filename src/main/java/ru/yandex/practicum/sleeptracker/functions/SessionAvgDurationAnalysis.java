package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class SessionAvgDurationAnalysis implements SleepingAnalysis<Integer> {
    private static final String ANALYSIS_DESCRIPTION = "Средняя продолжительность сессии (в минутах)";

    @Override
    public SleepAnalysisResult<Integer> get(TreeMap<LocalDateTime, SleepingSession> sessions) {

        int sessionsNumber = sessions.size();

        if (sessionsNumber == 0) {
            return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION);
        } else {
            int result = sessions.values().stream()
                    .mapToInt(SleepingSession::getDurationInMinutes)
                    .sum();
            return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION, result / sessionsNumber);
        }
    }
}
