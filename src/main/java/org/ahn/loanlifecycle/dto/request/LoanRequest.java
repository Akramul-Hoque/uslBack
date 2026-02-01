package org.ahn.loanlifecycle.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class LoanRequest {
    
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "0.01", message = "Principal amount must be greater than 0")
    private BigDecimal principalAmount;
    
    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
    private BigDecimal interestRate;
    
    @NotNull(message = "Tenure months is required")
    @Positive(message = "Tenure months must be positive")
    private Integer tenureMonths;
    
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
}
