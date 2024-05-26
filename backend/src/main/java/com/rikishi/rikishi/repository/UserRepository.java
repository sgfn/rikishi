package com.rikishi.rikishi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikishi.rikishi.model.User;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class UserRepository extends JSONRepository<User, Long> {
    public UserRepository(ObjectMapper mapper) throws IOException {
        super(User.class, mapper);
    }
}
