package com.rikishi.rikishi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikishi.rikishi.ResourceManager;
import com.rikishi.rikishi.model.Indexable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JSONRepositoryTest {
    private final ObjectMapper mapper;

    public JSONRepositoryTest(@Autowired ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void defaultFile() {
        assertEquals(new File("data/value.json"), JSONRepository.defaultFile(Value.class));
    }

    @Test
    void findById() throws IOException {
        var repo = mkRepository();
        var opt = repo.findById(0);

        assertTrue(opt.isPresent());

        var value = opt.orElseThrow();
        assertEquals(0, value.id());
        assertEquals("Foo", value.value());

        var optNone = repo.findById(5);

        assertTrue(optNone.isEmpty());
    }

    @Test
    void findAll() throws IOException {
        var repo = mkRepository();
        var all = repo.findAll();
        assertEquals(4, all.count());
    }

    @Test
    void removeById() throws IOException {
        var repo = mkRepository();
        assertEquals(0, repo.removeById(0).getId());
        assertEquals(3, repo.findAll().count());
        assertNull(repo.removeById(0));
    }

    @Test
    void add() throws IOException {
        var repo = mkRepository();
        assertEquals(4, repo.findAll().count());

        var value = new Value(4, "Quax");

        assertEquals(value, repo.add(value));
        assertEquals(5, repo.findAll().count());
        assertEquals(value, repo.findById(4).orElseThrow());
    }

    @Test
    void remove() throws IOException {
        var repo = mkRepository();
        var value = repo.findById(0).orElseThrow();

        assertEquals(4, repo.findAll().count());
        assertTrue(repo.remove(value));
        assertEquals(3, repo.findAll().count());
    }

    @Test
    void save() throws IOException {
        var writer = new StringWriter();

        var repo = mkRepository(writer);
        repo.save();

        assertEquals("""
            [{"id":0,"value":"Foo"},{"id":1,"value":"Bar"},{"id":2,"value":"Baz"},{"id":3,"value":"Qux"}]""",
            writer.toString()
        );
    }

    private TestRepository mkRepository(Writer writer) throws IOException {
        var repo = new TestRepository(
            ResourceManager.getReader("/testRepository.json"),
            writer,
            mapper
        );
        repo.load();
        return repo;
    }

    private TestRepository mkRepository() throws IOException {
        return mkRepository(FileWriter.nullWriter());
    }

    private record Value(
        int id,
        String value
    ) implements Indexable<Integer> {
        @Override
        public Integer getId() {
            return id;
        }
    }

    private static class TestRepository extends JSONRepository<Value, Integer> {
        public TestRepository(Reader reader, Writer writer, ObjectMapper mapper) {
            super(Value.class, reader, writer, mapper);
        }
    }
}
