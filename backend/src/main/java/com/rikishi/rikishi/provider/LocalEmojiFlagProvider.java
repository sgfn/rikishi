package com.rikishi.rikishi.provider;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class LocalEmojiFlagProvider implements EmojiFlagProvider {
    private final Map<String, String> flags = new HashMap<>();

    public LocalEmojiFlagProvider() throws IOException {
        try (var stream = getClass().getResourceAsStream("/flags.csv")) {
            assert stream != null;

            var reader = new CSVReader(new InputStreamReader(stream));

            for (var line : reader) {
                flags.put(line[0], line[1]);
            }
        }
    }

    @Override
    public String flagOf(String countryCode) {
        return flags.getOrDefault(countryCode.toUpperCase(), "");
    }
}
