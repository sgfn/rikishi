package com.rikishi.rikishi.model;

import com.rikishi.rikishi.model.entity.Duel;

public record Fight(
    long id,
    User firstUser,
    User secondUser,
    int number,
    WeightClass weightClass,
    // -1 if no winner. Yes I know it's an anti-pattern, don't have time to correct it
    long winnerId
) implements Indexable<Long> {
    public static Fight fromJson(
        Duel duel,
        User resolvedFirstUser,
        User resolvedSecondUser,
        WeightClass resolvedWeightClass
    ) {
        return new Fight(
            duel.id(),
            resolvedFirstUser,
            resolvedSecondUser,
            duel.number(),
            resolvedWeightClass,
            duel.winnerId()
        );
    }

    public Duel toJson() {
        return new Duel(
            firstUser.id(),
            secondUser.id(),
            number,
            id,
            weightClass.name(),
            winnerId
        );
    }

    @Override
    public Long getId() {
        return id;
    }
}
