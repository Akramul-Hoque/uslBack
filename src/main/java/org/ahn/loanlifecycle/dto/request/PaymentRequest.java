package org.ahn.loanlifecycle.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PaymentRequest {
    
    @NotNull(message = "Loan ID is required")
    private Long loanId;
    
    @NotNull(message = "Amount paid is required")
    @DecimalMin(value = "0.01", message = "Amount paid must be greater than 0")
    private BigDecimal amountPaid;
    
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
}
