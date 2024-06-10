package com.rikishi.rikishi.system;

import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.entity.Duel;

import java.util.*;

public class AllVsAllManager implements MatchingSystem {
    private AllVsAll matchingSystem;
    private Map<User, Integer> finalStandings;

    public AllVsAllManager() {
        matchingSystem = new AllVsAll();
        finalStandings = new HashMap<>();
    }

    @Override
    public void nextMatch() {
        try {
            matchingSystem.nextMatch();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            handleTournamentEnd();
        }
    }

    private void handleTournamentEnd() {
        Set<User> winners = determineWinners();
        if (winners.size() == 1) {
            printStandings();
        } else {
            System.out.println("Tie detected. Running tie-breaker round.");
            matchingSystem = new AllVsAll();
            matchingSystem.loadPlayers(winners);
        }
    }

    private void printStandings() {
        List<Map.Entry<User, Integer>> result = new ArrayList<>(finalStandings.entrySet());

        result.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        Map<User, Integer> sortedMapByValue = new LinkedHashMap<>();
        for (Map.Entry<User, Integer> entry : result) {
            sortedMapByValue.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<User, Integer> entry : sortedMapByValue.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        finalStandings = sortedMapByValue;
    }

    public Map<User, Integer> getFinalStandings() {
        return finalStandings;
    }


    private Set<User> determineWinners() {
        int maxWins = matchingSystem.getMostWins();
        Map<User, Integer> playerWins = matchingSystem.getPlayerWins();

        Set<User> winners = new HashSet<>();
        for (Map.Entry<User, Integer> entry : playerWins.entrySet()) {
            if (finalStandings.containsKey(entry.getKey())) {
                finalStandings.put(entry.getKey(), finalStandings.get(entry.getKey()) + playerWins.get(entry.getKey()));
            } else {
                finalStandings.put(entry.getKey(), entry.getValue());
            }
            if (entry.getValue() == maxWins) {
                winners.add(entry.getKey());
            }
        }

        return winners;
    }

    @Override
    public void chooseWinner(User winner) {
        matchingSystem.chooseWinner(winner);
    }

    @Override
    public Set<User> getCurrentPlayers() {
        return matchingSystem.getCurrentPlayers();
    }

    @Override
    public void loadPlayers(Collection<User> players) {
        if (players.size() < 3 || players.size() > 5) {
            throw new IllegalArgumentException("Invalid number of players");
        }
        matchingSystem.loadPlayers(players);
    }

    public AllVsAll getMatchingSystem() {
        return matchingSystem;
    }

    public List<Duel> getDuels() {
        return matchingSystem.getDuels();
    }
}
