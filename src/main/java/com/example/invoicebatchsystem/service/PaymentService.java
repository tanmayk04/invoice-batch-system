package com.example.invoicebatchsystem.service;

import com.example.invoicebatchsystem.entity.Payment;

import com.example.invoicebatchsystem.repository.PaymentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
        }
    }
