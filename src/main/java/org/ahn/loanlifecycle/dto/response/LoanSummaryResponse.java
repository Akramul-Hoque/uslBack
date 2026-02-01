package org.ahn.loanlifecycle.dto.response;

import org.ahn.loanlifecycle.enums.LoanStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanSummaryResponse {
    
    private Long id;
    private String customerName;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private Integer tenureMonths;
    private LoanStatus status;
    private LocalDate createDate;
    private BigDecimal totalExpectedEmi;
    private BigDecimal totalPaid;
    private BigDecimal remainingBalance;
    
    public LoanSummaryResponse() {}
    
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
    
    public BigDecimal getTotalPaid() {
        return totalPaid;
    }
    
    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }
    
    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }
    
    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
