package com.rikishi.rikishi.model.entity;

public record Duel(
    long id1Contestant,
    String name1,
    long id2Contestant,
    String name2,
    int number,
//    score1 score2 tracks how many times a contestant won in a round
    int score1,
    int score2,
    long id,
    String weightCategory,
    // -1 if no winner. Yes I know it's an anti-pattern, don't have time to correct it
    long winnerId
) {}
