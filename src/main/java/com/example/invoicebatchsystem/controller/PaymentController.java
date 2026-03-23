package com.example.invoicebatchsystem.controller;

import com.example.invoicebatchsystem.entity.Payment;

import com.example.invoicebatchsystem.service.InvoiceService;
import com.example.invoicebatchsystem.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayments();
    }
}
