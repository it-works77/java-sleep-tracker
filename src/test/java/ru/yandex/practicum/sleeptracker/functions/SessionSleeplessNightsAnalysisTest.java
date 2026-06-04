package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class SessionSleeplessNightsAnalysisTest {
    static final DateTimeFormatter LOG_DATETIME = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    static TreeMap<LocalDateTime, SleepingSession> sessions;
    static SessionSleeplessNightsAnalysis func;

    @BeforeAll
    static void setUpAll() {
        sessions = new TreeMap<>();
        func = new SessionSleeplessNightsAnalysis();
    }

    @BeforeEach
    void setUp() {
        sessions.clear();
    }

    @Test
    void getSleeplessNightsNumberForEmptySessions() {
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(Optional.empty(), result.getOrEmpty());
    }

    @Test
    void getFiveDaysStartAfterTwelve() {
        // Первая сессия сна в файле началась после 12 дня, потенциальной ночью для сна считается следующая ночь
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 00:31",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("05.05.26 23:01",
                "06.05.26 00:01",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Всего 5, 3 бессонных
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(3, result.getOrEmpty().get());
    }

    @Test
    void getFiveDaysStartBeforeTwelve() {
        // Первая сессия сна в файле началась до 12 дня, потенциальной ночью для сна считается предыдущая ночь
        SleepingSession s = new SleepingSession("01.05.26 11:01",
                "02.05.26 00:31",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("05.05.26 23:01",
                "06.05.26 00:01",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Всего 6, 4 бессонных
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(4, result.getOrEmpty().get());
    }

    @Test
    void getFiveDaysStartAfterTwelveSleeplessWithDailySessions() {
        // Первая сессия сна в файле началась после 12 дня, потенциальной ночью для сна считается следующая ночь
        // Бессонная
        SleepingSession s = new SleepingSession("01.05.26 21:01",
                "01.05.26 23:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Дневная
        s = new SleepingSession("02.05.26 06:01",
                "02.05.26 23:59",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("05.05.26 23:01",
                "06.05.26 00:01",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Всего 5, 4 бессонных
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(4, result.getOrEmpty().get());
    }

    @Test
    void getOnlyOneSessionLessThanDayStartBeforeTwelve() {
        // Первая сессия сна в файле началась до 12 дня, потенциальной ночью для сна считается предыдущая ночь
        SleepingSession s = new SleepingSession("01.05.26 00:01",
                "01.05.26 06:00",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Integer> result = func.get(sessions);
        // Всего 1, 0 бессонных
        assertEquals(0, result.getOrEmpty().get());
    }

    @Test
    void getOnlyOneSessionLessThanDayStartAfterTwelve() {
        // Первая сессия сна в файле началась после 12 дня, потенциальной ночью для сна считается следующая ночь
        // Бессонная
        SleepingSession s = new SleepingSession("01.05.26 21:01",
                "01.05.26 23:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Integer> result = func.get(sessions);
        // Всего 1, 1 бессонная
        assertEquals(1, result.getOrEmpty().get());
    }
}