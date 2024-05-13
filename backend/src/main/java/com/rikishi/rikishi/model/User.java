package com.rikishi.rikishi.model;

public record User(
    long id,
    String name,
    String lastname,
    int age,
    double weight,
    String country,
    String photoLink
) {}
