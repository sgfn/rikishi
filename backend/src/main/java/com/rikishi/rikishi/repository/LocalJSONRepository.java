package com.rikishi.rikishi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikishi.rikishi.model.Indexable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LocalJSONRepository<T extends Indexable<ID>, ID> implements Repository<T, ID> {
    private final Map<ID, T> data = new HashMap<>();

    private final Reader reader;
    private final Writer writer;

    private final ObjectMapper mapper;
    private final Class<T> clazz;

    public LocalJSONRepository(Class<T> clazz, Reader reader, Writer writer, ObjectMapper mapper) {
        this.clazz = clazz;
        this.reader = reader;
        this.writer = writer;
        this.mapper = mapper;
    }

    public LocalJSONRepository(Class<T> clazz, File file, ObjectMapper mapper) throws IOException {
        this(clazz,
            new FileReader(existent(file)),
            new FileWriter(existent(file), true),
            mapper
        );
    }

    public LocalJSONRepository(Class<T> clazz, ObjectMapper mapper) throws IOException {
        this(clazz, defaultFile(clazz), mapper);
    }

    private static File existent(File file) throws IOException {
        var parent = file.getParentFile();

        if (!parent.exists())
            parent.mkdirs();

        if (!file.exists())
            Files.writeString(Path.of(file.toString()), "[]\n");

        return file;
    }

    static <T> File defaultFile(Class<T> clazz) {
        var pathname = String.format("data/%s.json", clazz.getSimpleName().toLowerCase());
        return new File(pathname);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Stream<T> find(Predicate<T> predicate) {
        return data.values().stream().filter(predicate);
    }

    @Override
    public T removeById(ID id) {
        return data.remove(id);
    }

    @Override
    public T add(@NotNull T value) {
        data.put(value.getId(), value);
        return value;
    }

    @Override
    public void addAll(Iterable<T> it) {
        it.forEach(value -> data.put(value.getId(), value));
    }

    @Override
    public boolean remove(@NotNull T value) {
        return removeById(value.getId()) != null;
    }

    @Override
    public void load() throws IOException {
        var array = (T[]) mapper.readerForArrayOf(clazz).readValue(reader);

        data.clear();

        for (var element : array) {
            data.put(element.getId(), element);
        }
    }

    @Override
    public void save() throws IOException {
        mapper.writeValue(writer, data.values());
    }
}
