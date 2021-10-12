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

    public String validatePayData(Integer paySum, User user, String inputNumber,
                                  com.example.payment_system.entity.Service selectedService) {
        if (paySum > user.getCash())
            return "Введенная сумма превышает Ваш баланс";
        if (paySum > selectedService.getMaxPay() || paySum < selectedService.getMinPay())
            return "Введенная сумма не соответствует тарифу услуги";
        if (inputNumber.length() > 40)
            return "Введенный номер слишком длинный (40+ символов)";
        return null;
    }



    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
