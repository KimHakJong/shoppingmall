package com.shopping.backend.repository;

import com.shopping.backend.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    // 기본적인 save(), findById(), deleteById() 등은 자동으로 제공됨
}