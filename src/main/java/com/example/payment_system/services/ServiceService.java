package com.example.payment_system.services;

import com.example.payment_system.entity.Service;
import com.example.payment_system.repository.ServiceRepository;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {
    ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    private final ServiceRepository serviceRepository;

    public List<String> findAllNames() {
        List<Service> services = serviceRepository.findAll();
        List<String> services_name = new ArrayList<String>();
        for (Service service : services) {
            services_name.add(service.getName());
        }

        return services_name;
    }

    public Service findByName(String name){
        return serviceRepository.findByName(name);
    }


}
