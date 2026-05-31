package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class SessionNumberAnalysis implements SleepingAnalysis {
    private static final String ANALYSIS_DESCRIPTION = "Хронотип пользователя";

    @Override
    public SleepAnalysisResult<Chronotype> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        // TODO Implement this!
        return new SleepAnalysisResult<Chronotype>(ANALYSIS_DESCRIPTION, Chronotype.EARLY_BIRD);
    }
}
