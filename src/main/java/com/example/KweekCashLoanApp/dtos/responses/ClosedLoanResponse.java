package com.example.KweekCashLoanApp.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClosedLoanResponse {
    private long loanId;
    private long loanRequestId;
    private long approvedRequestId;
    private long customerId;
    private String uniqueCode;
    private BigDecimal loanAmount;
    private String loanTenure;
    private BigDecimal balance;
    private BigDecimal totalAmountRepaid;
    private String purposeOfLoan;
    private String repaymentPreference;
    private String paymentMethod;
    private double interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amountPerPaymentPeriod;
    private LocalDate dateOfApplication;
    private LocalDate dateApproved;
}
