package com.example.payment_system.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;

    @Column(name = "pay_sum", length = 10, nullable = false)
    private Integer paySum;
    @Column(name = "input_number", length = 40, nullable = false)
    private String inputNumber;
    @Column(name = "local_date", columnDefinition = "DATE", nullable = false)
    private LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    public Payment(Integer paySum, String inputNumber, LocalDate localDate, User user, Service service) {

        this.paySum = paySum;
        this.inputNumber = inputNumber;
        this.localDate = localDate;
        this.user = user;
        this.service = service;
    }

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaySum() {
        return paySum;
    }

    public void setPaySum(Integer paySum) {
        this.paySum = paySum;
    }

    public String getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(String inputNumber) {
        this.inputNumber = inputNumber;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
