package com.example.invoicebatchsystem.repository;

import com.example.invoicebatchsystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repository for Invoice entity.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /*
     * Used to prevent duplicate invoice inserts
     */
    boolean existsByInvoiceNumber(String invoiceNumber);

    /*
     * We need this method so we can fetch a specific invoice
     * using invoice number like INV-001.
     *
     * Why Optional?
     * Because invoice may or may not exist.
     */
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}