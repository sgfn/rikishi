package com.rikishi.rikishi.config;

import com.rikishi.rikishi.loader.UserLoader;
import com.rikishi.rikishi.loader.csv.CSVUserLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserLoaderConfig {

    @Bean
    public UserLoader getUserLoader() {
        return new CSVUserLoader();
    }
}
