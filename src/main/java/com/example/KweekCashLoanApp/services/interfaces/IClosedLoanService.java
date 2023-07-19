package com.example.KweekCashLoanApp.services.interfaces;

import com.example.KweekCashLoanApp.dtos.responses.ClosedLoanResponse;

public interface IClosedLoanService {
    ClosedLoanResponse saveClosedLoan(ClosedLoanResponse closedLoan);
}
