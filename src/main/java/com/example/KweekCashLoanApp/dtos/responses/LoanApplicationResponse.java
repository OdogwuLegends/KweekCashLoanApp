package com.example.KweekCashLoanApp.dtos.responses;

import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanApplicationResponse {
    private String firstName;
    private long loanRequestId;
    private long customerId;
    private BigDecimal loanAmount;
    private String uniqueCode;
    private String loanTenure;
    private LoanStatus loanStatus;
    private String purposeOfLoan;
    private String repaymentPreference;
    private String paymentMethod;
    private LocalDate dateOfApplication;
    private String message;


    @Override
    public String toString(){
        return "Hello " + firstName + ", Your application has been received." +
                "\nYour unique code is " + uniqueCode +
                "\nPlease check your request status in 48 hours.";
    }
}
