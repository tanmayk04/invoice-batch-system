package com.example.invoicebatchsystem.repository;

/*
 * We import Payment because this repository will manage Payment table data.
 */
import com.example.invoicebatchsystem.entity.Invoice;
import com.example.invoicebatchsystem.entity.Payment;

/*
 * JpaRepository gives us built-in database methods like save(), findAll(), delete(), etc.
 */
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * This is a repository interface for Payment entity.
 *
 * Java concept:
 * Interface = contract
 *
 * Spring concept:
 * By extending JpaRepository, Spring gives us DB operations automatically.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /*
     * This is a derived query method.
     *
     * We are declaring it because we want to avoid inserting
     * the same payment again and again when the app restarts.
     *
     * Spring reads the method name and generates the query automatically.
     *
     * Meaning:
     * "Check if a payment exists with this paymentReference"
     */
    boolean existsByPaymentReference(String paymentReference);

    Optional<Payment> findByInvoice(Invoice invoice);
}