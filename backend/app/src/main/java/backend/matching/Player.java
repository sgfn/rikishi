package backend.matching;

import java.util.Objects;
import java.util.UUID;

public class Player {
    private final String name;

    public Player() {
        this.name = "Player-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
