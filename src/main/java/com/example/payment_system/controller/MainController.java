package com.example.payment_system.controller;


import com.example.payment_system.entity.Payment;
import com.example.payment_system.entity.Service;
import com.example.payment_system.entity.User;
import com.example.payment_system.services.PaymentService;
import com.example.payment_system.services.ServiceService;
import com.example.payment_system.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class MainController {
    private static final Logger logger = LogManager.getLogger();

    MainController(UserService userService,
                   ServiceService serviceService,
                   PaymentService paymentService) {

        this.userService = userService;
        this.serviceService = serviceService;
        this.paymentService = paymentService;
    }

    private final UserService userService;
    private final ServiceService serviceService;
    private final PaymentService paymentService;

    @GetMapping("/")
    public String greeting(Model model, Principal principal) {

        User logged_user = userService.findByPhoneNumber(principal.getName());

        model.addAttribute("name", logged_user.getPhoneNumber());
        model.addAttribute("cash", logged_user.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments", logged_user.getPayments());
        return "greeting";
    }

    @PostMapping("addPayment")////////////////////////////////////////////////////////
    public String addPayment(@RequestParam String inputNumber,
                             @RequestParam Integer paySum,
                             @RequestParam String serviceName,
                             Principal principal) {

        User logged_user = userService.findByPhoneNumber(principal.getName());
        logged_user.reduceCash(paySum);

        Service selectedService = serviceService.findByName(serviceName);
        Payment payment = new Payment(paySum, inputNumber, LocalDate.now(), logged_user, selectedService);
        paymentService.testPaymentAdd(payment);

        selectedService.addPayment(payment);
        logged_user.addPayment(payment);

        return "success-pay-page";
    }

    @PostMapping("payService")
    public String goToPayPage(@RequestParam String nameService,
                              Model model) {

        model.addAttribute("maxPay", serviceService.findByName(nameService).getMaxPay());
        model.addAttribute("minPay", serviceService.findByName(nameService).getMinPay());
        model.addAttribute("service", nameService);

        return "pay-page";
    }

    @PostMapping("filterPaySum")
    public String filterPaySum(@RequestParam Integer filterPaySumTextBox,
                               Principal principal,
                               Model model) {
        User logged_user = userService.findByPhoneNumber(principal.getName());
        model.addAttribute("name", logged_user.getPhoneNumber());
        model.addAttribute("cash", logged_user.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments", paymentService.findByPaySumAndUser(filterPaySumTextBox, logged_user));

        return "greeting";
    }

    @PostMapping("filterService")
    public String filterService(@RequestParam String filterServiceDropdown,
                                Principal principal,
                                Model model) {
        User logged_user = userService.findByPhoneNumber(principal.getName());
        model.addAttribute("name", logged_user.getPhoneNumber());
        model.addAttribute("cash", logged_user.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments", paymentService.findByServiceAndUser(serviceService.findByName(filterServiceDropdown), logged_user));

        return "greeting";
    }

    @PostMapping("filterDate")
    public String filterDate(@RequestParam String filterStartDateTextBox,
                             @RequestParam String filterEndDateTextBox,
                             Principal principal,
                             Model model) {
        User logged_user = userService.findByPhoneNumber(principal.getName());

        LocalDate startDate = LocalDate.parse(filterStartDateTextBox);
        LocalDate endDate = LocalDate.parse(filterEndDateTextBox);

        model.addAttribute("name", logged_user.getPhoneNumber());
        model.addAttribute("cash", logged_user.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments", paymentService.findByLocalDateBetweenAndUser(startDate, endDate, logged_user));

        return "greeting";
    }

}
