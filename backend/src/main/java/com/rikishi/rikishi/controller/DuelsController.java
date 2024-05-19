package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.entity.Duel;
import com.rikishi.rikishi.model.entity.Duels;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DuelsController {
    @GetMapping("/duels")
    public Duels getDuels() {
        // TODO: fetch list of duels
        return new Duels(new ArrayList<Duel>());
    }
}
