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

import static com.example.KweekCashLoanApp.utils.HardcodedValues.REPAYMENT_SCHEDULE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = REPAYMENT_SCHEDULE)
public class RepaymentSchedule {

    @Id
    @GeneratedValue
    private Long repaymentScheduleId;

    @Column(nullable = false)
    private Long loanId;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private LocalDate dateOfPayment;
}
