package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;

public interface ICustomerService {
    RegisterUserResponse registerCustomer(RegisterUserRequest request);
    LoginResponse logIn(LoginRequest request) throws ObjectNotFoundException;
    LoanApplicationResponse applyForALoan(LoanApplicationRequest request);
    LoanApplicationResponse checkApplicationStatus(LoanApplicationRequest request);
//    LoanAgreementResponse viewLoanAgreementForm(LoanApplicationRequest request);

    FindUserResponse findCustomerById(long id) throws ObjectNotFoundException;
    FindUserResponse findCustomerByEmail(String email) throws ObjectNotFoundException;
    UpdateUserResponse updateCustomerDetails(UpdateUserRequest request) throws ObjectNotFoundException;
    String makePayment(PaymentRequest request) throws ObjectNotFoundException;
    String checkLoanBalance(PaymentRequest request);
}
