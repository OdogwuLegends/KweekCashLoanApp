package com.example.KweekCashLoanApp.data.models;

import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PendingLoanRequests")
public class PendingLoanRequests {
    @Id
    @GeneratedValue
    private long loanRequestId;

    @Column(nullable = false)
    private long customerId;

    @Column(nullable = false)
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

    private String optionalMessage;

    @Column(nullable = false)
    private LocalDate dateOfApplication;
}
