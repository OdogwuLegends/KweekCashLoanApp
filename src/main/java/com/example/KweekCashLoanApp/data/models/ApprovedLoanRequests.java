package com.example.KweekCashLoanApp.data.models;

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
@Entity(name = "ApprovedRequests")
public class ApprovedLoanRequests {
    @Id
    @GeneratedValue
    private long approvedRequestId;

    @Column(nullable = false)
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
