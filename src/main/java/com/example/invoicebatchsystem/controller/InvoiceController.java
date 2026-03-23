package com.example.invoicebatchsystem.controller;

/*
 * Controller still returns Invoice data,
 * so we import Invoice entity.
 */
import com.example.invoicebatchsystem.entity.Invoice;

/*
 * Instead of repository, controller should now talk to service layer.
 * This is a more real-world architecture.
 */
import com.example.invoicebatchsystem.service.InvoiceService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @RestController means:
 * this class handles HTTP requests and returns JSON data.
 */
@RestController

/*
 * All endpoints in this controller start with /invoices
 */
@RequestMapping("/invoices")
public class InvoiceController {

    /*
     * Now controller depends on service, not repository.
     *
     * Why?
     * Because controller should handle request/response only,
     * and service should handle business logic.
     */
    private final InvoiceService invoiceService;

    /*
     * Constructor injection again.
     *
     * Spring injects InvoiceService automatically.
     */
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /*
     * This endpoint handles:
     * GET http://localhost:8080/invoices
     *
     * Controller does not fetch DB data directly anymore.
     * It delegates that responsibility to service layer.
     */
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }
}