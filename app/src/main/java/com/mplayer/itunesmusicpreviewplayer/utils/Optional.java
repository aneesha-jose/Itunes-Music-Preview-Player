package com.mplayer.itunesmusicpreviewplayer.utils;

/**
 * Created by Aneesha on 13/11/17.
 */

public class Optional<T> {

    private final T value;
    private T defaultObj;

    private Optional() {
        this.value = null;
    }

    private Optional(T value) {
        this.value = value;
    }

    private Optional(T value, T defaultObj) {
        this.value = value;
        this.defaultObj = defaultObj;
    }

    public static <T> Optional<T> empty() {
        return new Optional<>();
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T> orElse(T value, T defaultObj) {
        return new Optional<>(value, defaultObj);
    }

    public T get() {
        return value == null ? defaultObj : value;
    }

    public interface Action<T> {
        void apply(T value);
    }

    public void ifPresent(Action<T> action) {
        if (value != null) {
            action.apply(value);
        }
    }

}

