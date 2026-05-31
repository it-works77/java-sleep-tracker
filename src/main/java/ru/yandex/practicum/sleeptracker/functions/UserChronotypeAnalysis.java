package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class UserChronotypeAnalysis implements SleepingAnalysis {
    private static final String ANALYSIS_DESCRIPTION = "Хронотип пользователя";

    @Override
    public SleepAnalysisResult<Chronotype> get(TreeMap<LocalDateTime, SleepingSession> sessions) {
        // TODO Implement this!
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
        *  -
        * */
        Set<LocalDate> daySessions = sessions.values().stream()
        /* Исключаем дневные сессии сна */
                .filter(  s ->
                    s.getSessionStart().getDayOfYear() == s.getSessionEnd().getDayOfYear()
                        && s.getSessionStart().getHour() > 9
                        && s.getSessionEnd().getHour() < 22
                )
                .map(s -> LocalDate.from(s.getSessionStart()))
                .collect(Collectors.toSet());


        return new SleepAnalysisResult<Chronotype>(ANALYSIS_DESCRIPTION, Chronotype.EARLY_BIRD);
    }
}
