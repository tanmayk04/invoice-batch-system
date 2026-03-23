package com.example.invoicebatchsystem.service;

/*
 * We import Invoice because this service will work with invoice data.
 * Service methods will return Invoice objects or lists of Invoice objects.
 */
import com.example.invoicebatchsystem.entity.Invoice;

/*
 * We import InvoiceRepository because service layer does not talk
 * directly to database by itself.
 *
 * Instead:
 * Service -> Repository -> Database
 */
import com.example.invoicebatchsystem.repository.InvoiceRepository;

/*
 * Spring's @Service annotation marks this class as a service-layer component.
 *
 * Why we need this:
 * Spring must know that this class should be created and managed automatically.
 *
 * In simple words:
 * We are telling Spring:
 * "This class contains business logic."
 */
import org.springframework.stereotype.Service;

/*
 * We import List because our method will return multiple invoices.
 */
import java.util.List;

/*
 * @Service marks this class as service layer.
 *
 * Big project view:
 * In large applications, service classes are where business rules usually live.
 */
@Service
public class InvoiceService {

    /*
     * We keep repository as a private final field.
     *
     * Why private?
     * To keep encapsulation.
     *
     * Why final?
     * Because once repository is assigned through constructor,
     * it should not change.
     *
     * This is good practice in Java and Spring.
     */
    private final InvoiceRepository invoiceRepository;

    /*
     * This is constructor injection.
     *
     * Why we need this constructor:
     * Spring will create InvoiceService object and automatically pass
     * InvoiceRepository into it.
     *
     * Big flow:
     * Spring app starts -> creates repository bean -> creates service bean
     * -> injects repository into service
     */
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /*
     * This method represents business-level intention:
     * "Get all invoices"
     *
     * Right now it simply calls repository.findAll().
     *
     * But later this is the place where we can add:
     * - sorting
     * - filtering
     * - validation
     * - transformation
     * - business rules
     *
     * That is exactly why service layer exists.
     */
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}