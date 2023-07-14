package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.models.ApprovedLoanRequests;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.LoanApplicationResponse;

import java.util.List;

public interface IApprovedLoansService {
    ApprovedLoanResponse saveApprovedRequest(ApprovedLoanRequests requests);

    ApprovedLoanResponse findRequestByUniqueCode(LoanApplicationRequest request);

    List<ApprovedLoanRequests> findAllApprovedRequests();
}
