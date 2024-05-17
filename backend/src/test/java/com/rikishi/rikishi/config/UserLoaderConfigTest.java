package com.rikishi.rikishi.config;

import com.rikishi.rikishi.loader.UserLoader;
import com.rikishi.rikishi.loader.csv.CSVUserLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class UserLoaderConfigTest {
    private final UserLoader loader;

    public UserLoaderConfigTest(@Autowired UserLoader loader) {
        this.loader = loader;
    }

    @Test
    void isCSVUserLoaderActive() {
        assertInstanceOf(CSVUserLoader.class, loader);
    }
}
