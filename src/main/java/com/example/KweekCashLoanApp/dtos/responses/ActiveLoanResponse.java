package com.example.KweekCashLoanApp.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ActiveLoanResponse {

    private long loanId;
    private long loanRequestId;
    private long approvedRequestId;
    private long customerId;
    private String uniqueCode;
    private BigDecimal loanAmount;
    private String loanTenure;
    private BigDecimal balance;
    private BigDecimal totalAmountRepaid;
    private String loanStatus;
    private String purposeOfLoan;
    private String repaymentPreference;
    private String paymentMethod;
    private double interestRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amountPerPaymentPeriod;
    private LocalDate dateOfApplication;
    private LocalDate dateApproved;


    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();

        sb.append("\nLoan Request ID - ").append(loanRequestId);
        sb.append("\nApproved Request ID - ").append(approvedRequestId);
        sb.append("\nCustomer ID - ").append(customerId);
        sb.append("\nDate of Application - ").append(dateOfApplication);
        sb.append("\nDate Approved - ").append(dateApproved);
        sb.append("\nAmount Requested - ").append(loanAmount);
        sb.append("\nUnique Code - ").append(uniqueCode);
        sb.append("\nLoan Tenure - ").append(loanTenure);
        sb.append("\nLoan Status - ").append(loanStatus);
        sb.append("\nInterest Rate - ").append(interestRate).append("% per annum");
        sb.append("\nAmount expected per payment period - N").append(amountPerPaymentPeriod);
        sb.append("\nPurpose Of Loan - ").append(purposeOfLoan);
        sb.append("\nPreferred Repayment Option - ").append(repaymentPreference);
        sb.append("\nPreferred Payment Method - ").append(paymentMethod);
        sb.append("\nStart Date - ").append(startDate);
        sb.append("\nEnd Date - ").append(endDate);
        sb.append("\nBalance - ").append(balance);
        sb.append("\nTotal Amount Repaid - ").append(totalAmountRepaid);

        return sb.toString();

    }
}
