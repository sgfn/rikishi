package com.rikishi.rikishi.model.entity;

import java.util.List;

public record Duels(
    List<Duel> duels
) {
    public void add(Duel duel) {
        if (!this.duels.contains(duel)) {
            duels.add(duel);
        }
    }
}
