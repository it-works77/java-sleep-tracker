package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class SessionNumberAnalysis implements SleepingAnalysis {
    @Override
    public SleepAnalysisResult<Chronotype> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        // TODO Implement this!
        return new SleepAnalysisResult<Chronotype>("Хронотип пользователя", Chronotype.EARLY_BIRD);
    }
}
