package com.example.payment_system.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "phone_number", length = 20, nullable = false, unique = true)
    private String phoneNumber;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "password", length = 50, nullable = false)
    private String password;
    @Column(name = "cash", length = 10, nullable = false)
    private Integer cash;
    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "user")
    private Set<Payment> payments;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {
    }

    public User(String phoneNumber, String email, String password, Integer cash, Boolean active) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.cash = cash;
        this.active = active;


    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public void reduceCash(Integer pay_sum) {
        this.cash -= pay_sum;
    }
    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {this.payments.add(payment);}
}
