package com.shopping.backend.repository;

import com.shopping.backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UsersRepository {
    @Autowired
    private EntityManager entityManager;

    public void insertUser(Users user) {
        entityManager.persist(user);
    }
}