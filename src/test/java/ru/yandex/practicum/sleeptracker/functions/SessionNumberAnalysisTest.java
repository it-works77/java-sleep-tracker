package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;


class SessionNumberAnalysisTest {
    static final DateTimeFormatter LOG_DATETIME = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @Test
    void calculateSessionNumberForEmptySessions() {
        TreeMap<LocalDateTime, SleepingSession> sessions = new TreeMap<>();

        SessionNumberAnalysis func = new SessionNumberAnalysis();
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(0, result.getOrEmpty().get());
    }

    @Test
    void calculateSessionNumberForOneSession() {
        TreeMap<LocalDateTime, SleepingSession> sessions = new TreeMap<>();
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 02:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SessionNumberAnalysis func = new SessionNumberAnalysis();
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(1, result.getOrEmpty().get());
    }

    @Test
    void calculateSessionNumberForManySessions() {
        TreeMap<LocalDateTime, SleepingSession> sessions = new TreeMap<>();
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 02:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("03.05.26 23:01",
                "04.05.26 02:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SessionNumberAnalysis func = new SessionNumberAnalysis();
        SleepAnalysisResult<Integer> result = func.get(sessions);
        assertEquals(2, result.getOrEmpty().get());
    }
}