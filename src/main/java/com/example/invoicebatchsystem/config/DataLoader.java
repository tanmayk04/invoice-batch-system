package com.example.invoicebatchsystem.config;

import com.example.invoicebatchsystem.entity.Invoice;
import com.example.invoicebatchsystem.entity.Payment;
import com.example.invoicebatchsystem.repository.InvoiceRepository;
import com.example.invoicebatchsystem.repository.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/*
 * @Configuration tells Spring this class contains startup configuration logic.
 */
@Configuration
public class DataLoader {

    /*
     * @Bean tells Spring to run this method at application startup.
     *
     * We are asking Spring to inject both repositories because:
     * - InvoiceRepository is needed to load/save invoices
     * - PaymentRepository is needed to load/save payments
     */
    @Bean
    CommandLineRunner loadData(InvoiceRepository invoiceRepository,
                               PaymentRepository paymentRepository) {

        return args -> {

            /*
             * -----------------------------
             * PART 1: INSERT SAMPLE INVOICES
             * -----------------------------
             *
             * We keep this logic because payments depend on invoices.
             * If invoices do not exist, payments cannot be linked.
             */

            if (!invoiceRepository.existsByInvoiceNumber("INV-001")) {
                Invoice invoice1 = new Invoice();
                invoice1.setInvoiceNumber("INV-001");
                invoice1.setCustomerName("Amazon");
                invoice1.setAmount(new BigDecimal("5000.00"));
                invoice1.setDueDate(LocalDate.now().plusDays(10));
                invoice1.setStatus("PENDING");

                invoiceRepository.save(invoice1);
                System.out.println("Inserted INV-001");
            }

            if (!invoiceRepository.existsByInvoiceNumber("INV-002")) {
                Invoice invoice2 = new Invoice();
                invoice2.setInvoiceNumber("INV-002");
                invoice2.setCustomerName("Google");
                invoice2.setAmount(new BigDecimal("12000.00"));
                invoice2.setDueDate(LocalDate.now().plusDays(5));
                invoice2.setStatus("PAID");

                invoiceRepository.save(invoice2);
                System.out.println("Inserted INV-002");
            }

            /*
             * ----------------------------------------
             * PART 2: FETCH INVOICES TO LINK PAYMENTS
             * ----------------------------------------
             *
             * We need actual Invoice objects before creating Payment objects.
             * That is because Payment has:
             *
             * private Invoice invoice;
             *
             * So we fetch invoice rows from DB by invoice number.
             */
            Optional<Invoice> invoice1Optional = invoiceRepository.findByInvoiceNumber("INV-001");
            Optional<Invoice> invoice2Optional = invoiceRepository.findByInvoiceNumber("INV-002");

            /*
             * ---------------------------------
             * PART 3: INSERT PAYMENT FOR INV-001
             * ---------------------------------
             *
             * We insert only if:
             * 1. payment does not already exist
             * 2. invoice exists in DB
             */
            if (!paymentRepository.existsByPaymentReference("PAY-001") && invoice1Optional.isPresent()) {

                /*
                 * Now we safely get the actual Invoice object from Optional
                 * because we already checked isPresent().
                 */
                Invoice invoice1 = invoice1Optional.get();

                /*
                 * Create Payment object
                 */
                Payment payment1 = new Payment();
                payment1.setPaymentReference("PAY-001");
                payment1.setAmountPaid(new BigDecimal("3000.00"));
                payment1.setPaymentDate(LocalDate.now());

                /*
                 * This is the most important line:
                 * we connect this payment to invoice INV-001.
                 *
                 * In Java:
                 * payment1 holds an Invoice object
                 *
                 * In DB:
                 * JPA stores the related invoice id in invoice_id column
                 */
                payment1.setInvoice(invoice1);

                /*
                 * Save payment into DB
                 */
                paymentRepository.save(payment1);
                System.out.println("Inserted PAY-001 for INV-001");
            }

            /*
             * ---------------------------------
             * PART 4: INSERT PAYMENT FOR INV-002
              * ---------------------------------
             */
            if (!paymentRepository.existsByPaymentReference("PAY-002") && invoice2Optional.isPresent()) {
                Invoice invoice2 = invoice2Optional.get();

                Payment payment2 = new Payment();
                payment2.setPaymentReference("PAY-002");
                payment2.setAmountPaid(new BigDecimal("12000.00"));
                payment2.setPaymentDate(LocalDate.now());
                payment2.setInvoice(invoice2);

                paymentRepository.save(payment2);
                System.out.println("Inserted PAY-002 for INV-002");
            }

            System.out.println("Sample invoice and payment loading completed.");
        };
    }
}