package com.example.invoicebatchsystem.entity;

/*
 * We first put this class in the same 'entity' package
 * because Payment is also a database table entity,
 * just like Invoice.
 */
import jakarta.persistence.*;

/*
 * We use BigDecimal for money values.
 * Why not double?
 * Because double can give precision issues for currency.
 * In finance-related data, BigDecimal is safer.
 */
import java.math.BigDecimal;

/*
 * We use LocalDate because payment date is a date value.
 * It stores only date, not time.
 */
import java.time.LocalDate;

/*
 * @Entity tells Spring/JPA:
 * "This Java class should be treated like a database table."
 */
@Entity

/*
 * @Table lets us control the exact table name in PostgreSQL.
 * We are calling the table 'payments'.
 */
@Table(name = "payments")
public class Payment {

    /*
     * Every table should have a primary key.
     * @Id marks this field as the primary key.
     */
    @Id

    /*
     * We want the database to generate the id automatically.
     * IDENTITY means PostgreSQL will auto-increment it.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * We keep a payment reference like PAY-001.
     * This is similar to invoice number, but for payments.
     *
     * nullable = false means this value is required.
     * unique = true means no two payments can have same reference.
     */
    @Column(name = "payment_reference", nullable = false, unique = true)
    private String paymentReference;

    /*
     * This field stores how much money was paid.
     * Again, BigDecimal is used because it is safer for money.
     */
    @Column(nullable = false)
    private BigDecimal amountPaid;

    /*
     * This field stores when the payment was made.
     */
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    /*
     * This is the most important line in this class.
     *
     * A payment belongs to one invoice.
     * That is why we use @ManyToOne:
     *
     * Many payments -> can belong to one invoice
     *
     * Even if today we insert one payment per invoice,
     * this design is more flexible and realistic.
     */
    @ManyToOne

    /*
     * @JoinColumn tells JPA which foreign key column should be created
     * in the payments table.
     *
     * So PostgreSQL will create a column named invoice_id
     * and use it to connect each payment to an invoice row.
     */
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    /*
     * JPA needs a default constructor.
     * It uses it internally while creating objects from database rows.
     */
    public Payment() {
    }

    /*
     * Getter for id.
     * We usually do not create a setter for id because database manages it.
     */
    public Long getId() {
        return id;
    }

    /*
     * Getter and setter for paymentReference
     * so other classes can read and update it safely.
     */
    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    /*
     * Getter and setter for amountPaid
     */
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    /*
     * Getter and setter for paymentDate
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    /*
     * Getter and setter for invoice
     *
     * Notice:
     * The type is Invoice, not Long and not String.
     *
     * Why?
     * Because in Java, we want to connect Payment object
     * to an actual Invoice object.
     *
     * JPA will convert that object relationship
     * into a foreign key relationship in the database.
     */
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}