package org.ahn.loanlifecycle.repository;

import org.ahn.loanlifecycle.entity.Loan;
import org.ahn.loanlifecycle.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    Page<Loan> findByStatus(LoanStatus status, Pageable pageable);
    
    Optional<Loan> findByIdAndStatus(Long id, LoanStatus status);
}
