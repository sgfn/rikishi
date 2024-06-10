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
        } else if (numberOfPlayers <= 16) {
            return new TreeBracket();
        }
        throw new IllegalArgumentException(numberOfPlayers + " is too many players");
    }
}
