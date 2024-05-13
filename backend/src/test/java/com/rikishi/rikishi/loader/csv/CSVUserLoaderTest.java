package com.rikishi.rikishi.loader.csv;

import com.rikishi.rikishi.ResourceManager;
import com.rikishi.rikishi.model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class CSVUserLoaderTest {

    @Test
    void loadByPath() throws IOException, URISyntaxException {
        var usersIterable = new CSVUserLoader().load(ResourceManager.get("/users.csv"));
        var actual = StreamSupport.stream(usersIterable.spliterator(), false).toList();

        assertEquals(2, actual.size());

        var expected = Set.of(
            new User(0, "Janusz", "Kowalski", 16, 45.5, "pl", "https://img.freepik.com/free-psd/3d-illustration-human-avatar-profile_23-2150671142.jpg"),
            new User(1, "Anna", "Nowak", 18, 42.75, "pl", "https://img.freepik.com/free-photo/3d-illustration-cute-cartoon-girl-blue-jacket-glasses_1142-41044.jpg")
        );

        assertEquals(expected, new HashSet<>(actual));
    }

    @Test
    void loadByReader() throws URISyntaxException, IOException {
        var reader = Files.newBufferedReader(ResourceManager.get("/users.csv"));
        var usersIterable = new CSVUserLoader().load(reader);
        var actual = StreamSupport.stream(usersIterable.spliterator(), false).toList();
        assertEquals(2, actual.size());
    }
}
