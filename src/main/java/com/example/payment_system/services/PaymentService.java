package com.example.payment_system.services;

import com.example.payment_system.entity.Payment;
import com.example.payment_system.entity.User;
import com.example.payment_system.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    private final PaymentRepository paymentRepository;

    public List<Payment> findByPaySumAndUser(Integer paySum, User user){
        return paymentRepository.findByPaySumAndUser(paySum, user);
    }

    public List<Payment> findByServiceAndUser(com.example.payment_system.entity.Service service, User user){
        return paymentRepository.findByServiceAndUser(service, user);
    }

    public List<Payment> findByLocalDateBetweenAndUser(LocalDate startDate, LocalDate endDate, User user){
        return paymentRepository.findByLocalDateBetweenAndUser(startDate, endDate, user);
    }



    ///////////
    public void testPaymentAdd(Payment service2) {
        paymentRepository.save(service2);
    }


}
