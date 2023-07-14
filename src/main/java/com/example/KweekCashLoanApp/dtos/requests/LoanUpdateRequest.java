package com.example.KweekCashLoanApp.dtos.requests;

import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import lombok.Data;

@Data
public class LoanUpdateRequest {
    private long loanRequestId;
    private LoanStatus loanStatus;
    private double interestRate;
    private String startDate;
    private String endDate;
    private String dateApproved;
    private String authorizationCode;
    private String adminLoginCode;
    private String message;
}
