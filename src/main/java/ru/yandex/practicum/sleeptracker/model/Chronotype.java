package ru.yandex.practicum.sleeptracker.model;

public enum Chronotype {
    EARLY_BIRD("Жаворонок"),
    NIGHT_OWL("Сова"),
    DOVE("Голубь");

    private final String name;

    Chronotype(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
