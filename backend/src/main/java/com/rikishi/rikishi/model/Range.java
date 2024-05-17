package com.rikishi.rikishi.model;

/**
 * Represents a range of type <T>.
 * @param <T> The type of elements in the range.
 * @param from The start of the range, inclusive. A `null` value represents negative infinity (`-inf`).
 * @param to The end of the range, inclusive. A `null` value represents positive infinity (`+inf`).
 */
public record Range<T extends Comparable<T>>(
    T from,
    T to
) {
    public Range() {
        this(null, null);
    }

    public boolean contains(T t) {
        return (from == null || from.compareTo(t) <= 0) && (to == null || t.compareTo(to) <= 0);
    }
}
