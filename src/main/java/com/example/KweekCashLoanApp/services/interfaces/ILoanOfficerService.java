package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;

import java.util.List;

public interface ILoanOfficerService {
    RegisterUserResponse registerLoanOfficer(RegisterUserRequest request);
    UpdateUserResponse updateLoanOfficerDetails(UpdateUserRequest request) throws ObjectNotFoundException;
    LoginResponse login(LoginRequest request) throws ObjectNotFoundException;
    List<PendingLoanResponse> seePendingLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException;
    List<ApprovedLoanResponse> seeApprovedLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException;
    List<RejectedLoanResponse> seeRejectedLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException;
    List<ActiveLoanResponse> seeActiveLoans(LoanUpdateRequest request) throws ObjectNotFoundException;
    String reviewLoanRequest(LoanUpdateRequest request) throws ObjectNotFoundException;
    LoanAgreementResponse generateLoanAgreementForm(LoanApplicationRequest request) throws ObjectNotFoundException;
}
