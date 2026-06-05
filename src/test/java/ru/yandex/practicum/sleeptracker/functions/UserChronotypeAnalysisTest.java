package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class UserChronotypeAnalysisTest {
    static final DateTimeFormatter LOG_DATETIME = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    static TreeMap<LocalDateTime, SleepingSession> sessions;
    static UserChronotypeAnalysis func;

    @BeforeAll
    static void setUpAll() {
        sessions = new TreeMap<>();
        func = new UserChronotypeAnalysis();
    }

    @BeforeEach
    void setUp() {
        sessions.clear();
    }

    @Test
    void getSleeplessNightsNumberForEmptySessions() {
        SleepAnalysisResult<Chronotype> result = func.get(sessions);
        assertEquals(Optional.empty(), result.getOrEmpty());
    }

    @Test
    void getOneNightOwl() {
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 09:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Chronotype> result = func.get(sessions);
        assertEquals(Chronotype.NIGHT_OWL, result.getOrEmpty().get());
    }

    @Test
    void getOneEarlyBird() {
        SleepingSession s = new SleepingSession("01.05.26 21:59",
                "02.05.26 06:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Chronotype> result = func.get(sessions);
        assertEquals(Chronotype.EARLY_BIRD, result.getOrEmpty().get());
    }

    @Test
    void getDovesOnBorderCases() {
        // Not Night owl
        SleepingSession s = new SleepingSession("01.05.26 23:00",
                "02.05.26 09:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("01.05.26 23:01",
                "02.05.26 09:00",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Not early bird
        s = new SleepingSession("01.05.26 22:00",
                "02.05.26 06:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("01.05.26 21:59",
                "02.05.26 07:00",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Chronotype> result = func.get(sessions);
        assertEquals(Chronotype.DOVE, result.getOrEmpty().get());
    }

    @Test
    void getNightOwlWithSleeplessNightsAndDailySessions() {
        // Two Night owls
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 09:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("02.05.26 23:01",
                "03.05.26 09:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // An early bird
        s = new SleepingSession("04.05.26 21:59",
                "05.05.26 06:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Daily session (have to be excluded)
        s = new SleepingSession("04.05.26 11:59",
                "04.05.26 13:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Three sleepless nights (have to be excluded)
        s = new SleepingSession("05.05.26 11:59",
                "05.05.26 13:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("06.05.26 11:59",
                "06.05.26 13:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        s = new SleepingSession("07.05.26 11:59",
                "07.05.26 13:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Dove with dates skipping
        s = new SleepingSession("15.05.26 11:59",
                "15.05.26 13:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Chronotype> result = func.get(sessions);
        assertEquals(Chronotype.NIGHT_OWL, result.getOrEmpty().get());
    }

    @Test
    void getDovesWhenGreater() {
        // Night owl
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 09:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // An early bird
        s = new SleepingSession("04.05.26 21:59",
                "05.05.26 06:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Two doves with dates skipping
        // Not Night owl
        s = new SleepingSession("15.05.26 23:00",
                "15.05.26 09:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // Not early bird
        s = new SleepingSession("16.05.26 22:00",
                "17.05.26 06:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Chronotype> result = func.get(sessions);
        assertEquals(Chronotype.DOVE, result.getOrEmpty().get());
    }

    @Test
    void getDovesWhenEqualNumberOfEarlyBirdAndOwls() {
        // Night owl
        SleepingSession s = new SleepingSession("01.05.26 23:01",
                "02.05.26 09:01",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        // An early bird
        s = new SleepingSession("04.05.26 21:59",
                "05.05.26 06:59",
                "BAD", LOG_DATETIME);
        sessions.put(s.getSessionStart(), s);

        SleepAnalysisResult<Chronotype> result = func.get(sessions);
        assertEquals(Chronotype.DOVE, result.getOrEmpty().get());
    }
}