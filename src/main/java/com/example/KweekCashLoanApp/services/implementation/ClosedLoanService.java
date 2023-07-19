package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.models.ClosedLoans;
import com.example.KweekCashLoanApp.data.repositories.ClosedLoanRepository;
import com.example.KweekCashLoanApp.dtos.responses.ClosedLoanResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClosedLoanService implements IClosedLoanService{
    @Autowired
    ClosedLoanRepository closedLoanRepository;

    @Override
    public ClosedLoanResponse saveClosedLoan(ClosedLoanResponse closedLoan) {
        ClosedLoans finishedLoan = new ClosedLoans();
        BeanUtils.copyProperties(closedLoan,finishedLoan);
        ClosedLoans savedFinishedLoan = closedLoanRepository.save(finishedLoan);
        ClosedLoanResponse response = new ClosedLoanResponse();
        BeanUtils.copyProperties(savedFinishedLoan,response);
        return response;
    }
}
