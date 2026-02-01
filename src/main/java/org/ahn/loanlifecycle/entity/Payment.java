package org.ahn.loanlifecycle.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long loanId;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amountPaid;
    
    @Column(nullable = false)
    private LocalDate paymentDate;
    
    public Payment() {
        this.paymentDate = LocalDate.now();
    }
    
    public Payment(Long loanId, BigDecimal amountPaid) {
        this();
        this.loanId = loanId;
        this.amountPaid = amountPaid;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getLoanId() {
        return loanId;
    }
    
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
    
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
