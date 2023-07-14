package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import com.example.KweekCashLoanApp.data.models.RejectedLoanRequests;

import java.util.List;

public interface IRejectedLoansService {
    String saveRejectedRequest(PendingLoanRequests foundRequest);

    List<RejectedLoanRequests> findAllRejectedRequest();
}
