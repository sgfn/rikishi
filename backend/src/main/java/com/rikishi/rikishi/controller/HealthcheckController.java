package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.controller.annotation.DebugOnly;
import com.rikishi.rikishi.model.entity.Healthcheck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@DebugOnly
public class HealthcheckController {
    @GetMapping("/health")
    public Healthcheck getHealth() {
        return new Healthcheck();
    }
}
