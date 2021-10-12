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

/**
 * Контроллер для главной страницы и страницы оплаты услуги
 */
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

    /**
     * Отображение основной информации о пользователе, списка услуг и истории совершенных оплат
     */
    @GetMapping("/")
    public String greeting(Model model, Principal principal) {

        User loggedUser = userService.findByPhoneNumber(principal.getName());

        model.addAttribute("name", loggedUser.getPhoneNumber());
        model.addAttribute("cash", loggedUser.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments", loggedUser.getPayments());
        return "greeting";
    }

    /**
     * Оплата выбранной услуги
     */
    @PostMapping("addPayment")
    public String addPayment(@RequestParam String inputNumber,
                             @RequestParam Integer paySum,
                             @RequestParam String serviceName,
                             Principal principal,
                             Model model) {

        User loggedUser = userService.findByPhoneNumber(principal.getName());
        Service selectedService = serviceService.findByName(serviceName);

        String message = paymentService.validatePayData(paySum, loggedUser, inputNumber, selectedService);
        if (message != null)
        {
            model.addAttribute("maxPay", serviceService.findByName(serviceName).getMaxPay());
            model.addAttribute("minPay", serviceService.findByName(serviceName).getMinPay());
            model.addAttribute("service", serviceName);
            model.addAttribute("message", message);
            return "/pay-page";
        }

        loggedUser.reduceCash(paySum);


        Payment payment = new Payment(paySum, inputNumber, LocalDate.now(), loggedUser, selectedService);
        paymentService.savePayment(payment);

        selectedService.addPayment(payment);
        loggedUser.addPayment(payment);


        return "success-pay-page";
    }

    /**
     * Переход на страницу оплаты выбранной услуги
     */
    @PostMapping("goToPayPage")
    public String goToPayPage(@RequestParam String nameService,
                              Model model) {

        model.addAttribute("maxPay", serviceService.findByName(nameService).getMaxPay());
        model.addAttribute("minPay", serviceService.findByName(nameService).getMinPay());
        model.addAttribute("service", nameService);

        return "pay-page";
    }

    /**
     * Фильтр списка оплат услуг по сумме оплаты
     */
    @PostMapping("filterPaySum")
    public String filterPaySum(@RequestParam Integer filterPaySumTextBox,
                               Principal principal,
                               Model model) {
        User loggedUser = userService.findByPhoneNumber(principal.getName());
        model.addAttribute("name", loggedUser.getPhoneNumber());
        model.addAttribute("cash", loggedUser.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments", paymentService.findByPaySumAndUser(filterPaySumTextBox, loggedUser));

        return "greeting";
    }

    /**
     * Фильтр списка оплат услуг по наименованию услуги
     */
    @PostMapping("filterService")
    public String filterService(@RequestParam String filterServiceDropdown,
                                Principal principal,
                                Model model) {
        User loggedUser = userService.findByPhoneNumber(principal.getName());
        model.addAttribute("name", loggedUser.getPhoneNumber());
        model.addAttribute("cash", loggedUser.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments",
                paymentService.findByServiceAndUser(serviceService.findByName(filterServiceDropdown), loggedUser));

        return "greeting";
    }

    /**
     * Фильтр списка оплат услуг по дате (с-до)
     */
    @PostMapping("filterDate")
    public String filterDate(@RequestParam String filterStartDateTextBox,
                             @RequestParam String filterEndDateTextBox,
                             Principal principal,
                             Model model) {
        User loggedUser = userService.findByPhoneNumber(principal.getName());

        LocalDate startDate = LocalDate.parse(filterStartDateTextBox);
        LocalDate endDate = LocalDate.parse(filterEndDateTextBox);

        model.addAttribute("name", loggedUser.getPhoneNumber());
        model.addAttribute("cash", loggedUser.getCash());
        model.addAttribute("servicesName", serviceService.findAllNames());
        model.addAttribute("payments",
                paymentService.findByLocalDateBetweenAndUser(startDate, endDate, loggedUser));

        return "greeting";
    }

}
