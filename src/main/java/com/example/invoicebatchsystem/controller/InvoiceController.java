package com.example.invoicebatchsystem.controller;

import com.example.invoicebatchsystem.entity.Invoice;
import com.example.invoicebatchsystem.repository.InvoiceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @RestController
 * Tells Spring this class handles REST API requests
 * and returns data directly as JSON
 */
@RestController

/*
 * Base URL for all methods in this controller
 * So all endpoints here will start with /invoices
 */
@RequestMapping("/invoices")
public class InvoiceController {

    // Repository reference to talk to database
    private final InvoiceRepository invoiceRepository;

    /*
     * Constructor Injection
     * Spring automatically gives the repository object here
     */
    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /*
     * @GetMapping
     * Handles HTTP GET request for:
     * http://localhost:8080/invoices
     */
    @GetMapping
    public List<Invoice> getAllInvoices() {
        // findAll() comes from JpaRepository
        return invoiceRepository.findAll();
    }
}