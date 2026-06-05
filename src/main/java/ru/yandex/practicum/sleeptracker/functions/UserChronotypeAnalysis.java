package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class UserChronotypeAnalysis implements SleepingAnalysis {
    private static final String ANALYSIS_DESCRIPTION = "Хронотип пользователя";

    @Override
    public SleepAnalysisResult<Chronotype> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        /*
         * Для каждой ночи на основе времени засыпания и пробуждения определите,
         * относится ночь к типу «сова», «жаворонок» или «голубь».
         * - «Сова» — если время засыпания было после 23:00, а время пробуждения — после 9:00.
         * - «Жаворонок» — если время засыпания было до 22:00, а время пробуждения до — 7:00.
         * - «Голубь» — во всех остальных случаях.
         * Бессонные ночи и дневные сессии сна в подсчёте должны игнорироваться.
         * */

        /*
         * Дневная сессия:
         *  - время засыпания и пробуждения в одни сутки
         *  - время засыпания после 9:00
         *  - время пробуждения до 22:00
         * Бессонная ночь:
         *  - лёг спать в один день, а проснулся на следующий
         *  - лёг спать после 00:00, но до 6:00
         * */

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION);
        }

        List<SleepingSession> sessionsWithoutSleeplessNightsAndDaily = sessions.values().stream()
                /* Исключаем дневные сессии сна
                 * Интервал ночного сна с 6:00 до 00:00.
                 * Достаточно проверить, что уснул после 6:00 и проснулся в тот же день.
                 * Это будет дневная сессия, исключаем */
                .filter(s -> !(s.getSessionStart().getDayOfYear() == s.getSessionEnd().getDayOfYear()
                                && s.getSessionStart().getHour() >= 6
                        )
                )
                // Исключаем бессонные ночи. Берем те, где пользователь спал (переход через дату или уснул до 6)
                .filter(s ->
                        // лёг спать в один день, а проснулся на следующий
                        (s.getSessionEnd().getDayOfYear() == s.getSessionStart().getDayOfYear() + 1)
                                // или лёг спать после 00:00, но до 6:00, проснулся в тот же день
                                || (s.getSessionEnd().getDayOfYear() == s.getSessionStart().getDayOfYear()
                                && s.getSessionStart().getHour() < 6)
                )
                .toList();

        List<SleepingSession> nightOwlSessions = sessionsWithoutSleeplessNightsAndDaily.stream()
                .filter(s ->
                        // Уснул после 23, проснулся на следующий день после 9 утра
                        (s.getSessionEnd().getDayOfYear() == s.getSessionStart().getDayOfYear() + 1
                                && s.getSessionStart().getHour() == 23
                                && s.getSessionStart().getMinute() > 0
                                && s.getSessionEnd().getHour() >= 9
                                && s.getSessionEnd().getMinute() > 0
                        )
                                // или уснул после 00:00 включительно до 9:00 и проснулся после 9 утра
                                || (s.getSessionStart().getHour() < 9
                                && s.getSessionEnd().getHour() >= 9
                                && s.getSessionEnd().getMinute() > 0
                        )
                )
                .toList();

        List<SleepingSession> earlyBirdSessions = sessionsWithoutSleeplessNightsAndDaily.stream()
                .filter(s ->
                        // Уснул раньше 22, проснулся на следующий день до 7 утра
                        s.getSessionEnd().getDayOfYear() == s.getSessionStart().getDayOfYear() + 1
                                && s.getSessionStart().getHour() < 22
                                && s.getSessionEnd().getHour() < 7
                )
                .toList();

        int totalNights = sessionsWithoutSleeplessNightsAndDaily.stream()
                .map(session -> LocalDate.from(session.getSessionStart()))
                .collect(Collectors.toSet())
                .size();

        int nightOwlDaysCount = nightOwlSessions.stream()
                .map(session -> LocalDate.from(session.getSessionStart()))
                .collect(Collectors.toSet())
                .size();

        int earlyBirdDaysCount = earlyBirdSessions.stream()
                .map(session -> LocalDate.from(session.getSessionStart()))
                .collect(Collectors.toSet())
                .size();

        int doveDaysCount = totalNights - nightOwlDaysCount - earlyBirdDaysCount;

        if (nightOwlDaysCount > earlyBirdDaysCount && nightOwlDaysCount > doveDaysCount) {
            return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION, Chronotype.NIGHT_OWL);
        } else if (earlyBirdDaysCount > nightOwlDaysCount && earlyBirdDaysCount > doveDaysCount) {
            return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION, Chronotype.EARLY_BIRD);
        } else {
            return new SleepAnalysisResult<>(ANALYSIS_DESCRIPTION, Chronotype.DOVE);
        }
    }
}
