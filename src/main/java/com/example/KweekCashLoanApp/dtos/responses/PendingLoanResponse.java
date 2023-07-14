package com.example.KweekCashLoanApp.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PendingLoanResponse {
    private String firstName;
    private String lastName;
    private String address;
    private String occupation;
    private long customerId;
    private long loanRequestId;
    private BigDecimal loanAmount;
    private String uniqueCode;
    private String loanTenure;
    private String loanStatus;
    private String purposeOfLoan;
    private String repaymentPreference;
    private String paymentMethod;
    private String optionalMessage;
    private LocalDate dateOfApplication;


    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();

        sb.append("\nName - ").append(firstName).append(" ").append(lastName);
        sb.append("\nAddress - ").append(address);
        sb.append("\nOccupation - ").append(occupation);
        sb.append("\nCustomer ID - ").append(customerId);
        sb.append("\nLoan Request ID - ").append(loanRequestId);
        sb.append("\nAmount Requested - ").append(loanAmount);
        sb.append("\nUnique Code - ").append(uniqueCode);
        sb.append("\nLoan Tenure - ").append(loanTenure);
        sb.append("\nLoan Status - ").append(loanStatus);
        sb.append("\nPurpose Of Loan - ").append(purposeOfLoan);
        sb.append("\nPreferred Repayment Option - ").append(repaymentPreference);
        sb.append("\nPreferred Payment Method - ").append(paymentMethod);
        sb.append("\nDate of Application - ").append(dateOfApplication).append("\n\n\n");

        return sb.toString();
    }
}
