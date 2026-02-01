package org.ahn.loanlifecycle.service;

import org.ahn.loanlifecycle.dto.request.LoanRequest;
import org.ahn.loanlifecycle.dto.response.LoanResponse;
import org.ahn.loanlifecycle.dto.response.LoanSummaryResponse;
import org.ahn.loanlifecycle.dto.request.PaymentRequest;
import org.ahn.loanlifecycle.entity.Loan;
import org.ahn.loanlifecycle.enums.LoanStatus;
import org.ahn.loanlifecycle.entity.Payment;
import org.ahn.loanlifecycle.repository.LoanRepository;
import org.ahn.loanlifecycle.repository.PaymentRepository;
import org.ahn.loanlifecycle.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    public MessageResponse createLoan(LoanRequest loanRequest) {
        Loan loan = new Loan(
            loanRequest.getCustomerName(),
            loanRequest.getPrincipalAmount(),
            loanRequest.getInterestRate(),
            loanRequest.getTenureMonths()
        );
        
        Loan savedLoan = loanRepository.save(loan);
        return new MessageResponse("Loan created successfully with ID: " + savedLoan.getId());
    }
    
    public Page<LoanResponse> getAllLoans(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Loan> loans = loanRepository.findAll(pageable);
        return loans.map(this::convertToLoanResponse);
    }
    
    public LoanSummaryResponse getLoanSummary(Long loanId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new RuntimeException("Loan not found with ID: " + loanId);
        }
        
        Loan loan = loanOpt.get();
        BigDecimal totalPaid = paymentRepository.sumAmountPaidByLoanId(loanId);
        BigDecimal remainingBalance = loan.getTotalExpectedEmi().subtract(totalPaid);
        
        LoanSummaryResponse summary = convertToLoanSummaryResponse(loan);
        summary.setTotalPaid(totalPaid);
        summary.setRemainingBalance(remainingBalance);
        
        return summary;
    }
    
    public MessageResponse addPayment(PaymentRequest paymentRequest) {
        Optional<Loan> loanOpt = loanRepository.findById(paymentRequest.getLoanId());
        if (loanOpt.isEmpty()) {
            throw new RuntimeException("Loan not found with ID: " + paymentRequest.getLoanId());
        }
        
        Loan loan = loanOpt.get();
        
        if (loan.getStatus() == LoanStatus.CLOSED) {
            throw new RuntimeException("Cannot add payment to a closed loan");
        }
        
        Payment payment = new Payment(paymentRequest.getLoanId(), paymentRequest.getAmountPaid());
        Payment savedPayment = paymentRepository.save(payment);
        
        updateLoanStatus(loan);
        
        return new MessageResponse("Payment of " + paymentRequest.getAmountPaid() + " added successfully for loan ID: " + paymentRequest.getLoanId());
    }
    
    private void updateLoanStatus(Loan loan) {
        BigDecimal totalPaid = paymentRepository.sumAmountPaidByLoanId(loan.getId());
        
        if (totalPaid.compareTo(loan.getTotalExpectedEmi()) >= 0) {
            loan.setStatus(LoanStatus.CLOSED);
        } else {
            LocalDate dueDate = loan.getCreateDate().plusMonths(1);
            if (LocalDate.now().isAfter(dueDate)) {
                loan.setStatus(LoanStatus.DEFAULTED);
            }
        }
        
        loanRepository.save(loan);
    }
    
    private LoanResponse convertToLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setCustomerName(loan.getCustomerName());
        response.setPrincipalAmount(loan.getPrincipalAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTenureMonths(loan.getTenureMonths());
        response.setStatus(loan.getStatus());
        response.setCreateDate(loan.getCreateDate());
        response.setTotalExpectedEmi(loan.getTotalExpectedEmi());
        return response;
    }
    
    private LoanSummaryResponse convertToLoanSummaryResponse(Loan loan) {
        LoanSummaryResponse response = new LoanSummaryResponse();
        response.setId(loan.getId());
        response.setCustomerName(loan.getCustomerName());
        response.setPrincipalAmount(loan.getPrincipalAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTenureMonths(loan.getTenureMonths());
        response.setStatus(loan.getStatus());
        response.setCreateDate(loan.getCreateDate());
        response.setTotalExpectedEmi(loan.getTotalExpectedEmi());
        return response;
    }
}
