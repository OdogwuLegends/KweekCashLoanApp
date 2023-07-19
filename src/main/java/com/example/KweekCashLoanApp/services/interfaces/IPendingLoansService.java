package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.models.Customer;
import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.requests.LoanUpdateRequest;
import com.example.KweekCashLoanApp.dtos.responses.LoanApplicationResponse;
import com.example.KweekCashLoanApp.dtos.responses.PendingLoanResponse;

import java.util.List;

public interface IPendingLoansService {
    LoanApplicationResponse requestForALoan(LoanApplicationRequest request, Customer foundCustomer);

    LoanApplicationResponse confirmStatus(LoanApplicationRequest request);
    PendingLoanResponse findRequestById(Long id);
    List<PendingLoanRequests> findAllPendingRequests();
    String updateRequestDetails(LoanUpdateRequest request);
}
