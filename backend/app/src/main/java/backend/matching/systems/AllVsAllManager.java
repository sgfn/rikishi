package backend.matching.systems;

import backend.matching.Player;

import java.util.*;

public class AllVsAllManager implements MatchingSystem {
    private AllVsAll matchingSystem;
    private Collection<Player> initialPlayers;
    private final Map<Player, Integer> finalStandings;

    public AllVsAllManager() {
        matchingSystem = new AllVsAll();
        finalStandings = new HashMap<>();
    }

    @Override
    public void nextMatch() {
        try {
            matchingSystem.nextMatch();
        } catch (RuntimeException e) {
            handleTournamentEnd();
        }
    }

    private void handleTournamentEnd() {
        Set<Player> winners = determineWinners();
        if (winners.size() == 1) {
            printStandings();
        } else {
            System.out.println("Tie detected. Running tie-breaker round.");
            matchingSystem = new AllVsAll();
            matchingSystem.loadPlayers(winners);
        }
    }

    private void printStandings() {
        List<Map.Entry<Player, Integer>> result = new ArrayList<>(finalStandings.entrySet());

        result.sort(Map.Entry.comparingByValue());
        Map<Player, Integer> sortedMapByValue = new LinkedHashMap<>();
        for (Map.Entry<Player, Integer> entry : result) {
            sortedMapByValue.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Player, Integer> entry : sortedMapByValue.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    private Set<Player> determineWinners() {
        int maxWins = matchingSystem.getMostWins();
        Map<Player, Integer> playerWins = matchingSystem.getPlayerWins();

        Set<Player> winners = new HashSet<>();
        for (Map.Entry<Player, Integer> entry : playerWins.entrySet()) {
            if (finalStandings.containsKey(entry.getKey())) {
                System.out.println(entry.getKey()+" -> "+finalStandings.get(entry.getKey()));
                finalStandings.put(entry.getKey(), finalStandings.get(entry.getKey()) + playerWins.get(entry.getKey()));
            } else {
                finalStandings.put(entry.getKey(), entry.getValue());
            }
            System.out.println(entry.getKey()+" -> "+finalStandings.get(entry.getKey()));
            if (entry.getValue() == maxWins) {
                winners.add(entry.getKey());
            }
        }

        return winners;
    }

    @Override
    public void chooseWinner(Player winner) {
        matchingSystem.chooseWinner(winner);
    }

    @Override
    public Set<Player> getCurrentPlayers() {
        return matchingSystem.getCurrentPlayers();
    }

    @Override
    public void loadPlayers(Collection<Player> players) {
        if (players.size() < 3 || players.size() > 5) {
            throw new IllegalArgumentException("Invalid number of players");
        }
        this.initialPlayers = players;
        matchingSystem.loadPlayers(players);
    }
}
