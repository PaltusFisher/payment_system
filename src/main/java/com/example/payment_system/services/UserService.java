package com.example.payment_system.services;

import com.example.payment_system.entity.User;
import com.example.payment_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    public User findByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public String generateRandomPassword(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    ///////////
    public void testUserAdd(User service2) {
        userRepository.save(service2);
    }
}
