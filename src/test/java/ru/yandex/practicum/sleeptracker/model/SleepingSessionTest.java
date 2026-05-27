package ru.yandex.practicum.sleeptracker.model;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class SleepingSessionTest {

    @Test
    void getSessionStart() {
    }

    @Test
    void getSessionEnd() {
    }

    @Test
    void getQuality() {
    }

    @Test
    void getDuration() {
        SleepingSession s = new SleepingSession("01.01.26 00:00",
                "01.01.26 00:10", "GOOD",
                DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
        assertEquals(10, s.getDuration().toMinutes());
    }

    @Test
    void getDurationInMinutes() {
        SleepingSession s = new SleepingSession("01.01.26 00:00",
                "02.01.26 00:00", "GOOD",
                DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));
        assertEquals(24 * 60, s.getDuration().toMinutes());
    }
}