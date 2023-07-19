package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.repositories.RepaymentScheduleRepository;
import com.example.KweekCashLoanApp.dtos.requests.PaymentRequest;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.RepaymentScheduleResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepaymentScheduleService implements IRepaymentScheduleService{
    @Autowired
    RepaymentScheduleRepository repaymentScheduleRepository;
    @Autowired
    ActiveLoansService activeLoansService;

    @Override
    public String makePayment(Long customerId, PaymentRequest request) throws ObjectNotFoundException {
        return activeLoansService.setNewBalance(customerId,request.getAmount());
    }

    @Override
    public RepaymentScheduleResponse checkBalance(Long customerId) {
        ActiveLoanResponse activeLoanResponse = activeLoansService.getLoanDetails(customerId);
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();
        BeanUtils.copyProperties(activeLoanResponse,response);
        return response;
    }
}
