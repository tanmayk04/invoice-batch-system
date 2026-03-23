package com.example.invoicebatchsystem.controller;

import com.example.invoicebatchsystem.dto.InvoiceReconciliationDto;
import com.example.invoicebatchsystem.service.ReconciliationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * This controller exposes reconciliation report through API.
 *
 * Business meaning:
 * Instead of raw tables, this endpoint returns useful finance status.
 */
@RestController
@RequestMapping("/reconciliation")
public class ReconciliationController {

    /*
     * Controller depends on reconciliation service.
     */
    private final ReconciliationService reconciliationService;

    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    /*
     * GET endpoint:
     * http://localhost:8080/reconciliation
     */
    @GetMapping
    public List<InvoiceReconciliationDto> getReconciliationReport() {
        return reconciliationService.getInvoiceReconciliationReport();
    }
}