package com.rikishi.rikishi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {
    @GetMapping("/health")
    public Healthcheck healthcheck() {
        return new Healthcheck();
    }
}
