package com.rikishi.rikishi.loader.csv;

import com.rikishi.rikishi.ResourceManager;
import com.rikishi.rikishi.model.Range;
import com.rikishi.rikishi.model.Sex;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.provider.ConfigProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CSVUserLoaderTest {
    private final static WeightClass maleClass = new WeightClass(
        0,
        "Men",
        Sex.MALE,
        new Range<>(),
        new Range<>()
    );

    private final static WeightClass femaleClass = new WeightClass(
        1,
        "Women",
        Sex.FEMALE,
        new Range<>(),
        new Range<>()
    );

    private final ConfigProvider configProvider;

    public CSVUserLoaderTest() {
        configProvider = mock(ConfigProvider.class);
        when(configProvider.getWeightClassById(0)).thenReturn(Optional.of(maleClass));
        when(configProvider.getWeightClassById(1)).thenReturn(Optional.of(femaleClass));
    }

    @Test
    void loadByPath() throws IOException, URISyntaxException {
        var usersIterable = new CSVUserLoader(configProvider).load(ResourceManager.get("/users.csv"));
        var actual = StreamSupport.stream(usersIterable.spliterator(), false).toList();

        assertEquals(2, actual.size());

        var expected = Set.of(
            new User(0, "Janusz", "Kowalski", 16, 45.5, maleClass, Sex.MALE, "pl", "https://img.freepik.com/free-psd/3d-illustration-human-avatar-profile_23-2150671142.jpg"),
            new User(1, "Anna", "Nowak", 18, 42.75, femaleClass, Sex.FEMALE, "pl", "https://img.freepik.com/free-photo/3d-illustration-cute-cartoon-girl-blue-jacket-glasses_1142-41044.jpg")
        );

        assertEquals(expected, new HashSet<>(actual));
    }

    @Test
    void loadByReader() throws URISyntaxException, IOException {
        var reader = Files.newBufferedReader(ResourceManager.get("/users.csv"));
        var usersIterable = new CSVUserLoader(configProvider).load(reader);
        var actual = StreamSupport.stream(usersIterable.spliterator(), false).toList();
        assertEquals(2, actual.size());
    }
}
