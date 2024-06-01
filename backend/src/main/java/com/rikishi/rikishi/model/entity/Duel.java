package com.rikishi.rikishi.model.entity;

public record Duel(
    long id1Contestant,
    long id2Contestant,
    int number,
    long id,
    String weightCategory,
    long winner
) {}
