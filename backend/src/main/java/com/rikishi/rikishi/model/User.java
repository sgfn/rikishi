package com.rikishi.rikishi.model;

import com.rikishi.rikishi.model.entity.Contestant;

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
) {
    public static User fromJson(Contestant contestant, WeightClass resolvedWeightClass) {
        return new User(
            contestant.id(),
            contestant.name(),
            contestant.surname(),
            contestant.age(),
            contestant.weight(),
            resolvedWeightClass,
            Sex.fromString(contestant.sex()).orElseThrow(),
            contestant.country(),
            contestant.image()
        );
    }

    public Contestant toJson() {
        return new Contestant(
            name, surname, sex.toString(), age, weight, weightClass.name(), country, photoLink, id
        );
    }
}
