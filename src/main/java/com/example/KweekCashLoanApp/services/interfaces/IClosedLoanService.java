package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.dtos.responses.ClosedLoanResponse;

public interface IClosedLoanService {
    ClosedLoanResponse saveClosedLoan(ClosedLoanResponse closedLoan);
}
