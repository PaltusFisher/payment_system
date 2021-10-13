package com.example.payment_system.services;

import com.example.payment_system.entity.Payment;
import com.example.payment_system.entity.User;
import com.example.payment_system.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
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

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
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

    public String validateRegistrationData(User user, String email, String phoneNumber) {
        User userFromDB = findByPhoneNumber(user.getPhoneNumber());
        if (userFromDB != null) {
            return "Пользователь уже существует";
        }
        if (StringUtils.isBlank(phoneNumber) || (StringUtils.isBlank(email))) {
            return "Введено пустое значение";
        }
        if (phoneNumber.length() > 20 || email.length() > 50) {
            return "Введенное значение превышает максимальную длину";
        }
        return null;
    }

    public void userSave(User user) {
        userRepository.save(user);
    }
}
