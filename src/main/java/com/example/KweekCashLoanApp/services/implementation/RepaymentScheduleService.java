package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.repositories.RepaymentScheduleRepository;
import com.example.KweekCashLoanApp.dtos.requests.PaymentRequest;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.RepaymentScheduleResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.IRepaymentScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepaymentScheduleService implements IRepaymentScheduleService {
    @Autowired
    RepaymentScheduleRepository repaymentScheduleRepository;
    @Autowired
    ActiveLoansService activeLoansService;

    @Override
    public String makePayment(Long customerId, PaymentRequest request) throws ObjectNotFoundException {
        return activeLoansService.setNewBalance(customerId,request.getAmount());
    }

    @Override
    public RepaymentScheduleResponse checkBalance(Long customerId) throws ObjectNotFoundException {
        ActiveLoanResponse activeLoanResponse = activeLoansService.getLoanDetails(customerId);
        if(activeLoanResponse == null){
            throw new ObjectNotFoundException("Request Not Found");
        }
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();
        BeanUtils.copyProperties(activeLoanResponse,response);
        return response;
    }
}
