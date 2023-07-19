package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.models.ActiveLoans;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface IActiveLoansService {
    ActiveLoanResponse saveActiveLoans(ApprovedLoanResponse response);
    ActiveLoanResponse findByCustomerId(Long id);
    List<ActiveLoans> findAllActiveLoans();
    String setNewBalance(Long customerId, BigDecimal amountPaid) throws ObjectNotFoundException;
    ActiveLoanResponse getLoanDetails(Long customerId);
}
