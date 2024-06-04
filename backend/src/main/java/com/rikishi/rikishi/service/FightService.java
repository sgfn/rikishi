package com.rikishi.rikishi.service;

import java.io.IOException;
import java.util.stream.Stream;
import java.util.*;

// import com.rikishi.rikishi.loader.FightLoader;
import com.rikishi.rikishi.repository.FightRepository;
import org.springframework.stereotype.Service;

import com.rikishi.rikishi.model.Fight;

@Service("ds")
public class FightService implements AutoCloseable {
    private final FightRepository fightRepository;
    // TODO: implement loader if needed
    // private final FightLoader fightLoader;

    public FightService(FightRepository fr/*, FightLoader fl*/) throws IOException {
        this.fightRepository = fr;
        // this.fightLoader = fl;

        // fl.load();
    }

    public void addFight(Fight fight) {
        fightRepository.add(fight);
    }

    public Stream<Fight> getFights() {
        return fightRepository.findAll();
    }

    public Optional<Fight> getFightById(long id) {
        return fightRepository.findById(id);
    }

    // public void importFromFile(Path path) throws IOException {
    //     fightRepository.addAll(fightLoader.load(path));
    // }

    @Override
    public void close() throws Exception {
        fightRepository.save();
    }
}
