package com.example.KweekCashLoanApp.utils;

import com.example.KweekCashLoanApp.data.models.Customer;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.responses.LoanApplicationResponse;

public class Map {
    public static LoanApplicationResponse loanReqToRes(LoanApplicationRequest request, Customer customer){
        LoanApplicationResponse response = new LoanApplicationResponse();

        response.setFirstName(customer.getFirstName());
        response.setUniqueCode(request.getUniqueCode());

        return response;
    }
}
