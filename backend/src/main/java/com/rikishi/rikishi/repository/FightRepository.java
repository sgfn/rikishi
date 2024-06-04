package com.rikishi.rikishi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikishi.rikishi.model.Fight;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class FightRepository extends JSONRepository<Fight, Long> {
    public FightRepository(ObjectMapper mapper) throws IOException {
        super(Fight.class, mapper);
    }
}
