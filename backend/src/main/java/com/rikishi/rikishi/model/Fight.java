package com.rikishi.rikishi.model;

import com.rikishi.rikishi.model.entity.Duel;

import java.util.Objects;

public record Fight(
    long id,
    User firstUser,
    User secondUser,
    int number,
    int score1,
    int score2,
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
            duel.score1(),
            duel.score2(),
            resolvedWeightClass,
            duel.winnerId()
        );
    }

    public Duel toJson() {
        return new Duel(
            firstUser.id(),
            firstUser.name(),
            secondUser.id(),
            secondUser.name(),
            number,
            score1,
            score2,
            id,
            weightClass.name(),
            winnerId
        );
    }

    @Override
    public Long getId() {
        return (long) Objects.hash(this.id, this.weightClass);
    }
}
