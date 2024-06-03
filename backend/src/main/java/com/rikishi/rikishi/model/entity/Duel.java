package com.rikishi.rikishi.model.entity;

public record Duel(
    long id1Contestant,
    long id2Contestant,
    int number,
    long id,
    String weightCategory,
    // -1 if no winner. Yes I know it's an anti-pattern, don't have time to correct it
    long winnerId
) {}
