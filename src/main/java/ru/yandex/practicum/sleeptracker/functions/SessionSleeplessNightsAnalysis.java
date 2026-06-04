package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SessionSleeplessNightsAnalysis implements SleepingAnalysis {
    private static final String ANALYSIS_DESCRIPTION = "Количество бессонных ночей";

    @Override
    public SleepAnalysisResult<Integer> get(TreeMap<LocalDateTime, SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION);
        }

        int totalNights = Period.between(LocalDate.from(sessions.firstEntry().getValue()
                        .getSessionStart()),
                LocalDate.from(sessions.lastEntry().getValue()
                        .getSessionEnd()))
                .getDays();

        List<SleepingSession> nightSessions = sessions.values().stream()
                /* Исключаем дневные сессии сна
                 * Интервал ночного сна с 6:00 до 00:00.
                 * Достаточно проверить, что уснул после 6:00 и проснулся в тот же день */
                .filter(s -> !(s.getSessionStart().getDayOfYear() == s.getSessionEnd().getDayOfYear()
                                && s.getSessionStart().getHour() >= 6
                        )
                )
                .peek(session -> System.out.println(session.getSessionStart() + " - " + session.getSessionEnd()))
                //  Оставляем ночные сессии
                .filter(s ->
                        // лёг спать в один день, а проснулся на следующий
                        (s.getSessionEnd().getDayOfYear() == s.getSessionStart().getDayOfYear() + 1)
                                // или лёг спать после 00:00, но до 6:00, проснулся в тот же день
                                || (s.getSessionEnd().getDayOfYear() == s.getSessionStart().getDayOfYear()
                                    && s.getSessionStart().getHour() < 6)
                )
                .toList();

        int nightsWithSleepCount = nightSessions.stream()
                .map(session -> LocalDate.from(session.getSessionStart()))
                .collect(Collectors.toSet())
                .size();

        // если первая сессия сна в файле началась после 12 дня,
        // потенциальной ночью для сна считается следующая ночь, а если до 12 — то предыдущая.
        if (sessions.firstEntry().getValue()
                .getSessionStart().getHour() < 12) {
            totalNights++;
        }

        // Если только одна сессия сна, длительность в днях будет ноль.
        // Но одна ночь должна быть засчитана (или предыдущая, или следующая)
        if (totalNights < 1) {
            totalNights = 1;
        }

        return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION, totalNights - nightsWithSleepCount);
    }
}