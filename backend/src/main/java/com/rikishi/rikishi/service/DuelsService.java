package com.rikishi.rikishi.service;

import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.entity.Duel;
import com.rikishi.rikishi.model.entity.Duels;
import com.rikishi.rikishi.system.TreeBracket;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service("ds")
public class DuelsService implements AutoCloseable{
    private final TreeBracket bracket = new TreeBracket();

    public void setDuels(List<User> users) {
        Collections.shuffle(users);
        bracket.loadPlayers(users);
    }

    public Optional<Duels> getRoundDuels() {
        return Optional.ofNullable(bracket.getActualRoundDuels());
    }

    public Optional<Duels> getAllDuels(){
        return Optional.ofNullable(bracket.getDuelsBracket());
    }

    public void updateRoundDuels(Duels duels){
        bracket.updateDuels(duels.duels());
    }

    @Override
    public void close() throws Exception {
    }
}
