package com.example.KweekCashLoanApp.services.interfaces;

import com.example.KweekCashLoanApp.dtos.requests.PaymentRequest;
import com.example.KweekCashLoanApp.dtos.responses.RepaymentScheduleResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;

public interface IRepaymentScheduleService {
    String makePayment(Long customerId, PaymentRequest request) throws ObjectNotFoundException;
    RepaymentScheduleResponse checkBalance(Long customerId) throws ObjectNotFoundException;
}
