package com.example.KweekCashLoanApp.data.models;


import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity(name = "Rejected_loan_requests")
public class RejectedLoanRequests {

    @Id
    @GeneratedValue
    private long loanRequestId;

    @Column(nullable = false)
    private long customerId;

    private String uniqueCode;

    @Column(nullable = false)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private String loanTenure;

    @Column(nullable = false)
    private LoanStatus loanStatus;

    @Column(nullable = false)
    private String purposeOfLoan;

    @Column(nullable = false)
    private String repaymentPreference;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate dateOfApplication;
}
