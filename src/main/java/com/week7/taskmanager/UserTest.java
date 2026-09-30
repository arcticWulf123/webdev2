package com.week7.taskmanager;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component 
public class UserTest implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional 
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("Dave");
        user.setEmail("dave@example.com");
        user.setCreatedAt(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
        entityManager.detach(user);
        user.setEmail("thisisdave@example.com");
    }
}
