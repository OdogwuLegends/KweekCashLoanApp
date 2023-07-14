package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.dtos.requests.PaymentRequest;
import com.example.KweekCashLoanApp.dtos.responses.RepaymentScheduleResponse;

public interface IRepaymentScheduleService {
    String makePayment(Long customerId, PaymentRequest request);
    RepaymentScheduleResponse checkBalance(Long customerId);
}
