package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.dtos.requests.PaymentRequest;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.RepaymentScheduleResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.IRepaymentScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static com.example.KweekCashLoanApp.utils.HardcodedValues.REQUEST_NOT_FOUND;

@Service
@AllArgsConstructor
public class RepaymentScheduleService implements IRepaymentScheduleService {
    private final ActiveLoansService activeLoansService;


    @Override
    public String makePayment(Long customerId, PaymentRequest request) throws ObjectNotFoundException {
        return activeLoansService.setNewBalance(customerId,request.getAmount());
    }

    @Override
    public RepaymentScheduleResponse checkBalance(Long customerId) throws ObjectNotFoundException {
        ActiveLoanResponse activeLoanResponse = activeLoansService.getLoanDetails(customerId);
        if(activeLoanResponse == null){
            throw new ObjectNotFoundException(REQUEST_NOT_FOUND);
        }
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();
        BeanUtils.copyProperties(activeLoanResponse,response);
        return response;
    }
}
