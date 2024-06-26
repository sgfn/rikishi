package com.rikishi.rikishi.loader.csv;

import com.opencsv.CSVReader;
import com.rikishi.rikishi.loader.UserLoader;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.provider.ConfigProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Loads userdata from a CSV file
 * @apiNote The CSV file should be without a header
 */
@Component
public class CSVUserLoader implements UserLoader {
    private final ConfigProvider configProvider;

    public CSVUserLoader(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    @Override
    public Iterable<User> load(Path path) throws IOException {
        var reader = Files.newBufferedReader(path);
        return load(reader);
    }

    @Override
    public Iterable<User> load(Reader reader) {
        return () -> new CSVUserIterator(new CSVReader(reader), configProvider);
    }
}
