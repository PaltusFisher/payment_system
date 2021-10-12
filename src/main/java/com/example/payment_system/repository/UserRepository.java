package com.example.payment_system.repository;

import com.example.payment_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);

}


