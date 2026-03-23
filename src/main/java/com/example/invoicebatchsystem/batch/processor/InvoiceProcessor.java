package com.example.invoicebatchsystem.batch.processor;

import com.example.invoicebatchsystem.batch.model.InvoiceCsvRow;
import com.example.invoicebatchsystem.entity.Invoice;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * @Component tells Spring:
 * "Create an object of this class automatically and manage it"
 *
 * Why?
 * Because later in batch configuration, Spring will need this processor.
 * We don’t want to manually create it using 'new'.
 */
@Component
public class InvoiceProcessor implements ItemProcessor<InvoiceCsvRow, Invoice> {

    /*
     * ItemProcessor<Input, Output>
     *
     * Input  → InvoiceCsvRow  (coming from CSV via Reader)
     * Output → Invoice        (going to DB via Writer)
     *
     * So this class converts:
     * CSV row object → Database entity
     */

    @Override
    public Invoice process(InvoiceCsvRow row) {

        /*
         * This method runs for EVERY row in the CSV file.
         *
         * Example:
         * If CSV has 5 rows → this method runs 5 times.
         */

        /*
         * STEP 1: Validation
         *
         * We check if the data is valid before inserting into DB.
         *
         * Why?
         * In real systems, data can be dirty or incorrect.
         * We should avoid saving bad data.
         */

        if (row.getAmount() == null || row.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            /*
             * Returning null in Spring Batch means:
             * "Skip this record"
             *
             * So this row will NOT go to the writer (DB).
             */
            return null;
        }

        /*
         * STEP 2: Create a new Invoice entity object
         *
         * Why?
         * Because database layer works with Invoice entity,
         * not with CSV model (InvoiceCsvRow).
         */
        Invoice invoice = new Invoice();

        /*
         * STEP 3: Mapping (Transformation)
         *
         * We copy values from CSV object → Invoice entity
         *
         * This is where we transform data format
         * from "input model" to "database model"
         */

        invoice.setInvoiceNumber(row.getInvoiceNumber());
        invoice.setCustomerName(row.getCustomerName());
        invoice.setAmount(row.getAmount());
        invoice.setDueDate(LocalDate.parse(row.getDueDate()));
        invoice.setStatus(row.getStatus());

        /*
         * STEP 4: Return the processed object
         *
         * This Invoice object will now go to the Writer
         * which will insert it into the database.
         */
        return invoice;
    }
}