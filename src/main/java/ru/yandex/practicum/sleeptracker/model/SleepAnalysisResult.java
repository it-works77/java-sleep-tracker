package ru.yandex.practicum.sleeptracker.model;

import java.util.Objects;
import java.util.Optional;

/**
 * пользователю должно быть понятно, что именно посчиталось. Чтобы этого добиться, можно добавить в результат
 * каждой функции её текстовое описание, кроме непосредственно вычисленного значения.
 * Для этого вам понадобится создать дополнительный класс-обёртку для возвращаемого значения.
 */
public class SleepAnalysisResult<T> {
    private final String description;
    private T result;

    public SleepAnalysisResult(String description, T result) {
        this.description = description;
        this.result = result;
    }

    public SleepAnalysisResult(String description) {
        this.description = description;
        this.result = null;
    }

    public String getDescription() {
        return description;
    }

    public Optional<T> getOrEmpty() {
        return result == null ? Optional.empty() : Optional.of(result);
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if (Objects.nonNull(result)) {
            return description + ": " + result;
        } else {
            return description + ": " + "неизвестный результат";
        }
    }
}
