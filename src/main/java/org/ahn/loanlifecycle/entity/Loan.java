package org.ahn.loanlifecycle.entity;

import jakarta.persistence.*;
import org.ahn.loanlifecycle.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String customerName;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal principalAmount;
    
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal interestRate;
    
    @Column(nullable = false)
    private Integer tenureMonths;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;
    
    @Column(nullable = false)
    private LocalDate createDate;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalExpectedEmi;
    
    public Loan() {
        this.status = LoanStatus.ACTIVE;
        this.createDate = LocalDate.now();
    }
    
    public Loan(String customerName, BigDecimal principalAmount, BigDecimal interestRate, Integer tenureMonths) {
        this();
        this.customerName = customerName;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.totalExpectedEmi = calculateTotalExpectedEmi();
    }
    
    private BigDecimal calculateTotalExpectedEmi() {
        BigDecimal monthlyRate = interestRate.divide(BigDecimal.valueOf(100), 6, BigDecimal.ROUND_HALF_UP)
                                           .divide(BigDecimal.valueOf(12), 6, BigDecimal.ROUND_HALF_UP);
        
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal denominator = onePlusRate.pow(tenureMonths).subtract(BigDecimal.ONE);
        
        if (denominator.compareTo(BigDecimal.ZERO) == 0) {
            return principalAmount;
        }
        
        BigDecimal emi = principalAmount.multiply(monthlyRate.multiply(onePlusRate.pow(tenureMonths)))
                                       .divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
        
        return emi.multiply(BigDecimal.valueOf(tenureMonths)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }
    
    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }
    
    public BigDecimal getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public Integer getTenureMonths() {
        return tenureMonths;
    }
    
    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }
    
    public LoanStatus getStatus() {
        return status;
    }
    
    public void setStatus(LoanStatus status) {
        this.status = status;
    }
    
    public LocalDate getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
    
    public BigDecimal getTotalExpectedEmi() {
        return totalExpectedEmi;
    }
    
    public void setTotalExpectedEmi(BigDecimal totalExpectedEmi) {
        this.totalExpectedEmi = totalExpectedEmi;
    }
}
