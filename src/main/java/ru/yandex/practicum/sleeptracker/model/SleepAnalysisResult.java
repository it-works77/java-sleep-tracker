package ru.yandex.practicum.sleeptracker.model;

/**
 * пользователю должно быть понятно, что именно посчиталось. Чтобы этого добиться, можно добавить в результат
 * каждой функции её текстовое описание, кроме непосредственно вычисленного значения.
 * Для этого вам понадобится создать дополнительный класс-обёртку для возвращаемого значения.
 */
public class SleepAnalysisResult<T> {
    private final String description;
    private final T result;

    public SleepAnalysisResult(String description, T result) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        return description + ": " + result;
    }
}
