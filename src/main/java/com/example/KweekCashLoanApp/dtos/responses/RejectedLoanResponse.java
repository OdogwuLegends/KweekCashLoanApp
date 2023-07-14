package com.example.KweekCashLoanApp.dtos.responses;

import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RejectedLoanResponse {
    private long loanRequestId;
    private long customerId;
    private String uniqueCode;
    private BigDecimal loanAmount;
    private String loanTenure;
    private LoanStatus loanStatus;
    private String purposeOfLoan;
    private String repaymentPreference;
    private String paymentMethod;
    private String message;
    private LocalDate dateOfApplication;


    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();

        sb.append("\nCustomer ID - ").append(customerId);
        sb.append("\nLoan Request ID - ").append(loanRequestId);
        sb.append("\nAmount Requested - ").append(loanAmount);
        sb.append("\nUnique Code - ").append(uniqueCode);
        sb.append("\nLoan Tenure - ").append(loanTenure);
        sb.append("\nLoan Status - ").append(loanStatus);
        sb.append("\nPurpose Of Loan - ").append(purposeOfLoan);
        sb.append("\nPreferred Repayment Option - ").append(repaymentPreference);
        sb.append("\nPreferred Payment Method - ").append(paymentMethod);
        sb.append("\nDate of Application - ").append(dateOfApplication);
        sb.append("\nRemark - ").append(message);

        return sb.toString();
    }
}
