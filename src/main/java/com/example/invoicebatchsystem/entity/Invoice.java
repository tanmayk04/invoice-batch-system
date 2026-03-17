package com.example.invoicebatchsystem.entity;

// JPA annotations (used to map Java class to database table)
import jakarta.persistence.*;

// Java concepts: BigDecimal (for money), LocalDate (for date)
import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * @Entity → marks this class as a database table
 */
@Entity

/*
 * @Table → defines table name in DB
 * If not given, Spring uses class name
 */
@Table(name = "invoices")
public class Invoice {

    /*
     * @Id → marks primary key
     */
    @Id

    /*
     * @GeneratedValue → auto-increment ID in DB
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * @Column → customize column
     * name → DB column name
     * nullable = false → cannot be null
     * unique = true → no duplicates allowed
     */
    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    /*
     * customer name column
     */
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    /*
     * BigDecimal → used for money (better than double)
     */
    @Column(nullable = false)
    private BigDecimal amount;

    /*
     * LocalDate → used for date (no time)
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /*
     * status like: PAID, PENDING, OVERDUE
     */
    @Column(nullable = false)
    private String status;

    /*
     * Default constructor (required by JPA)
     */
    public Invoice() {}

    // Getter for id (no setter for safety)
    public Long getId() {
        return id;
    }

    // Getter and setter methods

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}