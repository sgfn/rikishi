package com.rikishi.rikishi.model.entity;

// Json-ready version of User
public record Contestant(
    String name,
    String surname,
    String sex,
    int age,
    double weight,
    String weightCategory,
    String country,
    String image,
    long id
) {}
