package ru.yandex.practicum.sleeptracker.model;

    /**
     * Качество сна
     */
public enum SleepQuality {
    BAD("плохое"),
    GOOD("хорошее"),
    NORMAL("нормальное");

    private final String name;

    SleepQuality(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    }
