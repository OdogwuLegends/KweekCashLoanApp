package com.example.KweekCashLoanApp.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Active_Loans")
public class ActiveLoans {
    @Id
    @GeneratedValue
    private long loanId;

    @Column(nullable = false)
    private long loanRequestId;

    @Column(nullable = false)
    private long approvedRequestId;

    @Column(nullable = false)
    private long customerId;

    @Column(nullable = false)
    private String uniqueCode;

    @Column(nullable = false)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private String loanTenure;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal totalAmountRepaid = BigDecimal.valueOf(0);

    @Column(nullable = false)
    private String loanStatus;

    @Column(nullable = false)
    private String purposeOfLoan;

    @Column(nullable = false)
    private String repaymentPreference;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private double interestRate;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private BigDecimal amountPerPaymentPeriod;

    @Column(nullable = false)
    private LocalDate dateOfApplication;

    @Column(nullable = false)
    private LocalDate dateApproved;
}
