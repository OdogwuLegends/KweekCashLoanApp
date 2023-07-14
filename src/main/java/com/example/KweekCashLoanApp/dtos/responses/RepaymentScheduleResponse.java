package com.example.KweekCashLoanApp.dtos.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RepaymentScheduleResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal loanAmount;
    private BigDecimal balance;
    private BigDecimal totalAmountRepaid;

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();

        sb.append("\t\t\t\t\t\t\t\t\tLOAN DETAILS");

        sb.append("\n\nLOAN AMOUNT -> ").append(loanAmount);
        sb.append("\nTOTAL AMOUNT REPAID -> ").append(totalAmountRepaid);
        sb.append("\nBALANCE -> ").append(balance);
        sb.append("\nSTART DATE -> ").append(startDate);
        sb.append("\nEND DATE -> ").append(endDate);

        return sb.toString();
    }
}
