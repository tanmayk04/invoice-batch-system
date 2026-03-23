package com.example.invoicebatchsystem.dto;

/*
 * We use BigDecimal because this DTO includes money values
 * like invoice amount, payment amount, and remaining balance.
 */
import java.math.BigDecimal;

/*
 * This class is not a database table.
 * It is only used to send calculated reconciliation data in API response.
 *
 * That is why there are no JPA annotations like @Entity here.
 */
public class InvoiceReconciliationDto {

    /*
     * We keep invoice number so business users can identify the invoice.
     */
    private String invoiceNumber;

    /*
     * Customer name helps make the result more readable.
     */
    private String customerName;

    /*
     * Original invoice amount.
     */
    private BigDecimal invoiceAmount;

    /*
     * Amount paid against the invoice.
     * If no payment exists, this can be zero.
     */
    private BigDecimal paymentAmount;

    /*
     * Remaining amount = invoice amount - payment amount
     */
    private BigDecimal remainingAmount;

    /*
     * Calculated reconciliation status:
     * PAID / PARTIAL / UNPAID / OVERPAID
     */
    private String reconciliationStatus;

    /*
     * Default constructor
     */
    public InvoiceReconciliationDto() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getReconciliationStatus() {
        return reconciliationStatus;
    }

    public void setReconciliationStatus(String reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }

}