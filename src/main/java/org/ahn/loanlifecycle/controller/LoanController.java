package org.ahn.loanlifecycle.controller;

import jakarta.validation.Valid;
import org.ahn.loanlifecycle.dto.ApiResponse;
import org.ahn.loanlifecycle.dto.MessageResponse;
import org.ahn.loanlifecycle.dto.request.LoanRequest;
import org.ahn.loanlifecycle.dto.request.PaymentRequest;
import org.ahn.loanlifecycle.dto.response.LoanResponse;
import org.ahn.loanlifecycle.dto.response.LoanSummaryResponse;
import org.ahn.loanlifecycle.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class LoanController {
    
    @Autowired
    private LoanService loanService;
    @PostMapping("/loans")
    public ResponseEntity<ApiResponse<Object>> createLoan(
            @Valid @RequestBody LoanRequest loanRequest) {

        MessageResponse response = loanService.createLoan(loanRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, response.getMessage(), null));
    }

    @GetMapping("/loans")
    public ResponseEntity<ApiResponse<Page<LoanResponse>>> getAllLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<LoanResponse> loans = loanService.getAllLoans(page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Loans fetched successfully", loans)
        );
    }

    @PostMapping("/payments")
    public ResponseEntity<ApiResponse<Object>> addPayment(
            @Valid @RequestBody PaymentRequest paymentRequest) {

        MessageResponse response = loanService.addPayment(paymentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, response.getMessage(), null));
    }
    
    @GetMapping("/loans/{id}/summary")
    public ResponseEntity<ApiResponse<LoanSummaryResponse>> getLoanSummary(
            @PathVariable Long id) {

        LoanSummaryResponse summary = loanService.getLoanSummary(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Loan summary fetched successfully", summary)
        );
    }
}
