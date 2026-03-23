package com.example.invoicebatchsystem.batch.writer;

import com.example.invoicebatchsystem.entity.Invoice;
import com.example.invoicebatchsystem.repository.InvoiceRepository;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

/*
 * @Component tells Spring:
 * create and manage this writer object automatically.
 *
 * Why?
 * Because later batch step configuration will need this writer bean.
 */
@Component
public class InvoiceWriter implements ItemWriter<Invoice> {

    /*
     * Writer needs InvoiceRepository because repository is the layer
     * that talks to the database.
     *
     * Big flow:
     * Writer -> Repository -> Database
     */
    private final InvoiceRepository invoiceRepository;

    /*
     * Constructor injection:
     * Spring will automatically pass InvoiceRepository here.
     */
    public InvoiceWriter(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /*
     * write() is the method defined by ItemWriter interface.
     *
     * This method is called with a chunk of processed Invoice objects.
     *
     * Very important concept:
     * Batch usually does not write one record at a time.
     * It writes in groups called chunks.
     */
    @Override
    public void write(Chunk<? extends Invoice> chunk) {

        /*
         * chunk contains multiple Invoice objects that already passed
         * through the processor.
         *
         * We now save all of them into the database.
         */
        for (Invoice invoice : chunk.getItems()) {

            /*
             * Optional safety check:
             * avoid duplicate insert if invoice number already exists.
             *
             * Why?
             * Because invoice_number is unique in DB.
             */
            if (!invoiceRepository.existsByInvoiceNumber(invoice.getInvoiceNumber())) {
                invoiceRepository.save(invoice);
            }
        }
    }
}