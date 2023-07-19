package com.example.KweekCashLoanApp.services.interfaces;

import com.example.KweekCashLoanApp.data.models.Customer;
import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.requests.LoanUpdateRequest;
import com.example.KweekCashLoanApp.dtos.responses.LoanApplicationResponse;
import com.example.KweekCashLoanApp.dtos.responses.PendingLoanResponse;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;

import java.util.List;

public interface IPendingLoansService {
    LoanApplicationResponse requestForALoan(LoanApplicationRequest request, Customer foundCustomer);
    LoanApplicationResponse confirmStatus(LoanApplicationRequest request) throws IncorrectDetailsException;
    PendingLoanResponse findRequestById(Long id) throws ObjectNotFoundException;
    List<PendingLoanRequests> findAllPendingRequests();
    String updateRequestDetails(LoanUpdateRequest request) throws ObjectNotFoundException;
}
