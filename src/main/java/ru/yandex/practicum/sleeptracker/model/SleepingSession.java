package ru.yandex.practicum.sleeptracker.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Содержимое каждой сессии сна
 */
public class SleepingSession {
    private final LocalDateTime sessionStart;
    private final LocalDateTime sessionEnd;
    private final Duration duration;
    private final SleepQuality quality;

    public Duration getDuration() {
        return duration;
    }

    public int getDurationInMinutes() {
        return (int) duration.toMinutes();
    }

    public SleepingSession(String sessionStartText, String sessionEndText, String qualityText,
                           DateTimeFormatter logDatetime) {
        sessionStart = LocalDateTime.parse(sessionStartText.trim(), logDatetime);
        sessionEnd = LocalDateTime.parse(sessionEndText.trim(), logDatetime);
        quality = SleepQuality.valueOf(qualityText.trim());

        duration = Duration.between(sessionStart, sessionEnd);
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    public LocalDateTime getSessionEnd() {
        return sessionEnd;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        SleepingSession that = (SleepingSession) o;
        return sessionStart.equals(that.sessionStart) && sessionEnd.equals(that.sessionEnd) && quality == that.quality;
    }

    @Override
    public int hashCode() {
        int result = sessionStart.hashCode();
        result = 31 * result + sessionEnd.hashCode();
        result = 31 * result + quality.hashCode();
        return result;
    }
}
