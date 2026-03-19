package com.example.invoicebatchsystem.config;

import com.example.invoicebatchsystem.entity.Invoice;
import com.example.invoicebatchsystem.repository.InvoiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * @Configuration
 * Tells Spring this class contains configuration / startup logic
 */
@Configuration
public class DataLoader {

    /*
     * @Bean
     * Spring will run this code when the application starts
     */
    @Bean
    CommandLineRunner loadData(InvoiceRepository invoiceRepository) {

        /*
         * Dependency Injection:
         * Spring gives us the InvoiceRepository automatically
         */
        return args -> {

            /*
             * Check if INV-001 already exists
             * If not, create and save it
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

            /*
             * Check if INV-002 already exists
             * If not, create and save it
             */
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

            System.out.println("Sample invoice loading completed.");
        };
    }
}