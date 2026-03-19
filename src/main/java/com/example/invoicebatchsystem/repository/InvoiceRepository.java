package com.example.invoicebatchsystem.repository;

import com.example.invoicebatchsystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository interface for Invoice entity
 *
 * Java concept:
 * Interface = contract
 *
 * Spring concept:
 * By extending JpaRepository, we get built-in database methods like:
 * save(), findAll(), findById(), delete()
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /*
     * Derived Query Method
     *
     * Spring reads this method name and automatically creates the query.
     *
     * Meaning:
     * Check whether an invoice exists with the given invoiceNumber
     *
     * Return type:
     * boolean -> true or false
     */
    boolean existsByInvoiceNumber(String invoiceNumber);
}