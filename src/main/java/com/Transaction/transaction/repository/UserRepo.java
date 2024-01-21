package com.Transaction.transaction.repository;

import com.Transaction.transaction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    User findByPassword(String password);
    boolean existsByEmail(String email);
}
