package com.example.payment_system.repository;

import com.example.payment_system.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findByName(String name);

}
