package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class SessionBadQualityCounterAnalysisTest {
    static final DateTimeFormatter LOG_DATETIME = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    static TreeMap<LocalDateTime, SleepingSession> sessions;
    static SessionBadQualityCounterAnalysis func;

    @BeforeAll
    static void setUpAll() {
        sessions = new TreeMap<>();
        func = new SessionBadQualityCounterAnalysis();
    }

    @BeforeEach
    void setUp() {
        sessions.clear();
    }

    @Test
    void getTwoBadSessions() {
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 02:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("03.05.26 23:01",
                "04.05.26 02:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("05.05.26 23:01",
                "06.05.26 02:01",
                "GOOD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("07.05.26 23:01",
                "08.05.26 02:01",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Long> result = func.get(sessions);
        assertEquals(2, result.getOrEmpty().get());
    }

    @Test
    void getZeroBadSessions() {
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 02:01",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("03.05.26 23:01",
                "04.05.26 02:01",
                "GOOD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("05.05.26 23:01",
                "06.05.26 02:01",
                "GOOD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("07.05.26 23:01",
                "08.05.26 02:01",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Long> result = func.get(sessions);
        assertEquals(0, result.getOrEmpty().get());
    }
}