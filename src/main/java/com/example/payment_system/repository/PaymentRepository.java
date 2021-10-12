package com.example.payment_system.repository;

import com.example.payment_system.entity.Payment;
import com.example.payment_system.entity.Service;
import com.example.payment_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByPaySumAndUser(Integer paySum, User user);

    List<Payment> findByServiceAndUser(Service service, User user);

    List<Payment> findByLocalDateBetweenAndUser(LocalDate startDate, LocalDate endDate, User user);
}
