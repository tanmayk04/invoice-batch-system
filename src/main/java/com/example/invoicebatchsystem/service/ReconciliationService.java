package com.example.invoicebatchsystem.service;

import com.example.invoicebatchsystem.dto.InvoiceReconciliationDto;
import com.example.invoicebatchsystem.entity.Invoice;
import com.example.invoicebatchsystem.entity.Payment;
import com.example.invoicebatchsystem.repository.InvoiceRepository;
import com.example.invoicebatchsystem.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * This service is different from InvoiceService and PaymentService.
 *
 * Why?
 * Because this service is not about one table only.
 * It is about a business use case:
 * "Reconcile invoices against payments"
 *
 * In big projects, many important services are like this.
 */
@Service
public class ReconciliationService {

    /*
     * We need InvoiceRepository because reconciliation starts from invoices.
     */
    private final InvoiceRepository invoiceRepository;

    /*
     * We need PaymentRepository because we must compare each invoice
     * with its related payment.
     */
    private final PaymentRepository paymentRepository;

    /*
     * Constructor injection for both repositories.
     */
    public ReconciliationService(InvoiceRepository invoiceRepository,
                                 PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    /*
     * This method returns reconciliation result for all invoices.
     *
     * Big flow:
     * 1. fetch all invoices
     * 2. for each invoice, find related payment
     * 3. calculate paid/unpaid/partial status
     * 4. build DTO
     * 5. return final list
     */
    public List<InvoiceReconciliationDto> getInvoiceReconciliationReport() {

        /*
         * Fetch all invoices from database.
         */
        List<Invoice> invoices = invoiceRepository.findAll();

        /*
         * We create a new list to hold final report rows.
         * Each row will be one DTO object.
         */
        List<InvoiceReconciliationDto> report = new ArrayList<>();

        /*
         * Loop through each invoice one by one.
         *
         * Java concept:
         * enhanced for loop
         */
        for (Invoice invoice : invoices) {

            /*
             * Try to find payment linked to this invoice.
             * It may exist or may not exist, so Optional is used.
             */
            Optional<Payment> paymentOptional = paymentRepository.findByInvoice(invoice);

            /*
             * If no payment exists, we treat payment amount as zero.
             * This helps simplify reconciliation logic.
             */
            BigDecimal paymentAmount = BigDecimal.ZERO;

            /*
             * If payment exists, take amountPaid from payment object.
             */
            if (paymentOptional.isPresent()) {
                paymentAmount = paymentOptional.get().getAmountPaid();
            }

            /*
             * Remaining amount = invoice amount - payment amount
             */
            BigDecimal remainingAmount = invoice.getAmount().subtract(paymentAmount);

            /*
             * We now calculate business status.
             *
             * compareTo():
             * returns 0  -> equal
             * returns <0 -> smaller
             * returns >0 -> bigger
             *
             * We use compareTo for BigDecimal values.
             */
            String reconciliationStatus;

            if (paymentAmount.compareTo(BigDecimal.ZERO) == 0) {
                reconciliationStatus = "UNPAID";
            } else if (paymentAmount.compareTo(invoice.getAmount()) == 0) {
                reconciliationStatus = "PAID";
            } else if (paymentAmount.compareTo(invoice.getAmount()) < 0) {
                reconciliationStatus = "PARTIAL";
            } else {
                reconciliationStatus = "OVERPAID";
            }

            /*
             * Create DTO object for this one invoice's reconciliation result.
             */
            InvoiceReconciliationDto dto = new InvoiceReconciliationDto();

            /*
             * Fill DTO fields with invoice data + calculated values.
             */
            dto.setInvoiceNumber(invoice.getInvoiceNumber());
            dto.setCustomerName(invoice.getCustomerName());
            dto.setInvoiceAmount(invoice.getAmount());
            dto.setPaymentAmount(paymentAmount);
            dto.setRemainingAmount(remainingAmount);
            dto.setReconciliationStatus(reconciliationStatus);

            /*
             * Add this DTO row to final report list.
             */
            report.add(dto);
        }

        /*
         * Return the full report after loop is complete.
         */
        return report;
    }
}