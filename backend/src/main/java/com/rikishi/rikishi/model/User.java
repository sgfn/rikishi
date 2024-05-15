package com.rikishi.rikishi.model;

public record User(
    long id,
    String name,
    String surname,
    int age,
    double weight,
    Sex sex,
    String country,
    String photoLink
) {}
