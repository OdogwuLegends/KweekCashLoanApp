package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;

import java.util.List;

public interface ILoanOfficerService {
    RegisterUserResponse registerLoanOfficer(RegisterUserRequest request);
    UpdateUserResponse updateLoanOfficerDetails(UpdateUserRequest request);
    LoginResponse login(LoginRequest request);
    List<PendingLoanResponse> seePendingLoanRequests(LoanUpdateRequest request);
    List<ApprovedLoanResponse> seeApprovedLoanRequests(LoanUpdateRequest request);
    List<RejectedLoanResponse> seeRejectedLoanRequests(LoanUpdateRequest request);
    List<ActiveLoanResponse> seeActiveLoans(LoanUpdateRequest request);
    String reviewLoanRequest(LoanUpdateRequest request);
    LoanAgreementResponse generateLoanAgreementForm(LoanApplicationRequest request);
}
