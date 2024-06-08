package com.rikishi.rikishi.system;

import com.rikishi.rikishi.system.matching.MatchingSystem;
import com.rikishi.rikishi.system.matching.MatchingSystem_II;
import com.rikishi.rikishi.system.matching.TreeBracket;

public class SystemPicker {
    public static MatchingSystem_II pickSystem(int numberOfPlayers) {
        if (numberOfPlayers <= 0) {
            throw new IllegalArgumentException("Number of players must be greater than 0");
        } else if (numberOfPlayers <= 5) {
            throw new IllegalArgumentException("System Missing");
        } else if (numberOfPlayers <= 8) {
            return new TreeBracket();
        } else if (numberOfPlayers <= 16) {
            throw new IllegalArgumentException("System Missing");
        }
        throw new IllegalArgumentException("Too many players");
    }
}
