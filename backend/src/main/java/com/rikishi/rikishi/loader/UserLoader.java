package com.rikishi.rikishi.loader;

import com.rikishi.rikishi.model.User;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

/**
 * Loads (imports) userdata from a file/stream
 */
public interface UserLoader {
    Iterable<User> load(Path path) throws IOException;
    Iterable<User> load(Reader reader);
}
