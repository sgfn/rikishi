package com.rikishi.rikishi.model;

public record User(
    long id,
    String name,
    String surname,
    int age,
    double weight,
    WeightClass weightClass,
    Sex sex,
    String country,
    String photoLink
) {}
