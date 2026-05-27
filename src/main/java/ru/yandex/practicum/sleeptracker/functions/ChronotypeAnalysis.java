package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class ChronotypeAnalysis implements SleepingAnalysis {
    @Override
    public SleepAnalysisResult<Integer> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        return new SleepAnalysisResult<Integer>("Количество сессий сна", sessions.size());
    }
}
