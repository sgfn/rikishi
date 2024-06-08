package com.rikishi.rikishi.system;

import com.rikishi.rikishi.model.Range;
import com.rikishi.rikishi.model.Sex;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class AllVsAllTest {
    @Test
    void genrateDuelsTest() {
        User player1 = new User(1, "John", "Doe", 25, 80.0, new WeightClass(1, "Heavyweight", Sex.MALE, new Range<>(75.0, 100.0), new Range<>(18, 30)), Sex.MALE, "USA", "link1");
        User player2 = new User(2, "Jane", "Smith", 23, 75.0, new WeightClass(1, "Heavyweight", Sex.FEMALE, new Range<>(65.0, 90.0), new Range<>(18, 30)), Sex.FEMALE, "UK", "link2");
        User player3 = new User(3, "Bob", "Brown", 27, 78.0, new WeightClass(3, "Lightweight", Sex.MALE, new Range<>(60.0, 80.0), new Range<>(18, 30)), Sex.MALE, "CAN", "link3");
        User player4 = new User(4, "Bogagb", "Broaehtrwn", 24, 78.5, new WeightClass(3, "Lightweight", Sex.MALE, new Range<>(60.0, 80.0), new Range<>(18, 30)), Sex.MALE, "CAN", "link4");
        User player5 = new User(5, "Boashsdthsdb", "Browahbtsrbxtn", 67, 79.0, new WeightClass(3, "Lightweight", Sex.MALE, new Range<>(60.0, 80.0), new Range<>(18, 30)), Sex.MALE, "CAN", "link5");

        AllVsAllManager manager = new AllVsAllManager();
        manager.loadPlayers(Set.of(player1, player2, player3, player4, player5));
        System.out.println(manager.getDuels());
        manager.chooseWinner(player1);
        manager.chooseWinner(player2);
        manager.chooseWinner(player2);
        System.out.println("Duels after 1st match");
        System.out.println(manager.getDuels());
        manager.nextMatch();
        manager.chooseWinner(player4);
        manager.chooseWinner(player4);
        System.out.println("Duels after 2nd match");
        System.out.println(manager.getDuels());
    }
}
