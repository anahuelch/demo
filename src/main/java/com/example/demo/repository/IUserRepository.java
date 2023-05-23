package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, UserId> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
