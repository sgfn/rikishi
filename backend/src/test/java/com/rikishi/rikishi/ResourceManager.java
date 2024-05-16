package com.rikishi.rikishi;

import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;

public class ResourceManager {
    private static final Object o = new Dummy();

    private ResourceManager() {
        // empty
    }

    public static @NotNull Path get(String name) throws URISyntaxException {
        return Path.of(Objects.requireNonNull(o.getClass().getResource(name)).toURI());
    }

    private static class Dummy {}
}
