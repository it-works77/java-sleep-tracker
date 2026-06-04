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

class SessionMinDurationAnalysisTest {
    static final DateTimeFormatter LOG_DATETIME = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    static TreeMap<LocalDateTime, SleepingSession> sessions;
    static SessionMinDurationAnalysis func;

    @BeforeAll
    static void setUpAll() {
        sessions = new TreeMap<>();
        func = new SessionMinDurationAnalysis();
    }

    @BeforeEach
    void setUp() {
        sessions.clear();
    }

    @Test
    void getMinDurationForEmptySessions() {
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(Optional.empty(), result.getOrEmpty());
    }

    @Test
    void getMinDurationOfSessions() {
        SleepingSession s = new SleepingSession("03.05.26 23:31",
                "04.05.26 00:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("05.05.26 23:01",
                "06.05.26 00:31",
                "GOOD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("07.05.26 23:01",
                "09.05.26 23:01",
                "NORMAL", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(30, result.getOrEmpty().get());
    }

    @Test
    void getMinDurationOfOneMinute() {
        SleepingSession s = new SleepingSession("03.05.26 23:59",
                "04.05.26 00:00",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(1, result.getOrEmpty().get());
    }
}