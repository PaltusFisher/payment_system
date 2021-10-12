package com.example.payment_system.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer id;
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;
    @Column(name = "min_pay", length = 10, nullable = false, unique = false)
    private Integer minPay;
    @Column(name = "max_pay", length = 10, nullable = false, unique = false)
    private Integer maxPay;

    @OneToMany(mappedBy = "service")
    private Set<Payment> payments;

    public Service() {
    }

    public Service(String name, Integer minPay, Integer maxPay) {
        this.name = name;
        this.minPay = minPay;
        this.maxPay = maxPay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinPay() {
        return minPay;
    }

    public void setMinPay(Integer minPay) {
        this.minPay = minPay;
    }

    public Integer getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(Integer maxPay) {
        this.maxPay = maxPay;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {this.payments.add(payment);}

}
