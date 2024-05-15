package com.rikishi.rikishi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public enum Sex {
    MALE,
    FEMALE;

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return switch (this) {
            case MALE -> "m";
            case FEMALE -> "f";
        };
    }

    @Contract(pure = true)
    public static Optional<Sex> fromString(String s) {
        return switch (s) {
            case "m" -> Optional.of(MALE);
            case "f" -> Optional.of(FEMALE);
            default -> Optional.empty();
        };
    }

    @JsonCreator
    public static Sex forValue(String s) {
        return fromString(s).orElseThrow();
    }

    @JsonValue
    public String toValue() {
        return toString();
    }
}
