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
@Entity(name = "RepaymentSchedule")
public class RepaymentSchedule {

    @Id
    @GeneratedValue
    private long repaymentScheduleId;

    @Column(nullable = false)
    private long loanId;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private LocalDate dateOfPayment;
}
