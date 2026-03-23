package com.example.invoicebatchsystem.batch.model;

import java.math.BigDecimal;

/*
 * This class represents ONE row from CSV file.
 *
 * It is NOT a database entity.
 * It is only used for reading input data from file.
 *
 * Think:
 * CSV → this class → later converted to Invoice entity
 */
public class InvoiceCsvRow {

    /*
     * These field names MUST match CSV header names.
     */
    private String invoiceNumber;
    private String customerName;
    private BigDecimal amount;
    private String dueDate;
    private String status;

    /*
     * Default constructor is required for Spring mapping.
     */
    public InvoiceCsvRow() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}