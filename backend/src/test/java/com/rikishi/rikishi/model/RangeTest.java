package com.rikishi.rikishi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangeTest {
    @Test
    public void intRangeTest() {
        var range = new Range<>(1, 5);
        assertFalse(range.contains(0));
        assertTrue(range.contains(1));
        assertTrue(range.contains(2));
        assertTrue(range.contains(5));
        assertFalse(range.contains(6));
    }

    @Test
    public void intOpenLeftRangeTest() {
        var range = new Range<>(null, 5);
        assertTrue(range.contains(-10));
        assertTrue(range.contains(1));
        assertTrue(range.contains(2));
        assertTrue(range.contains(5));
        assertFalse(range.contains(6));
    }

    @Test
    public void intOpenRightRangeTest() {
        var range = new Range<>(1, null);
        assertFalse(range.contains(-10));
        assertTrue(range.contains(1));
        assertTrue(range.contains(2));
        assertTrue(range.contains(5));
        assertTrue(range.contains(6));
    }

    @Test
    public void intFullOpenRangeTest() {
        var range = new Range<Integer>();
        assertTrue(range.contains(-10));
        assertTrue(range.contains(1));
        assertTrue(range.contains(2));
        assertTrue(range.contains(5));
        assertTrue(range.contains(6));
    }
}
