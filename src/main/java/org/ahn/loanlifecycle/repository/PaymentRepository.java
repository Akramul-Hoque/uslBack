package org.ahn.loanlifecycle.repository;

import org.ahn.loanlifecycle.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    List<Payment> findByLoanId(Long loanId);
    
    @Query("SELECT COALESCE(SUM(p.amountPaid), 0) FROM Payment p WHERE p.loanId = :loanId")
    BigDecimal sumAmountPaidByLoanId(@Param("loanId") Long loanId);
}
