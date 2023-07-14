package com.example.KweekCashLoanApp.dtos.requests;

import com.example.KweekCashLoanApp.data.enums.LoanTenure;
import com.example.KweekCashLoanApp.data.enums.PaymentMethod;
import com.example.KweekCashLoanApp.data.enums.RepaymentPreference;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanApplicationRequest {
    private String email; //I put this to fetch the customer and set customerId
    private long customerId;
    private BigDecimal loanAmount;
    private String uniqueCode;
    private LoanTenure loanTenure;
    private String purposeOfLoan;
    private RepaymentPreference repaymentPreference;
    private PaymentMethod paymentMethod;
}
