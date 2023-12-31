package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.ClosedLoans;
import com.example.KweekCashLoanApp.data.repositories.ClosedLoanRepository;
import com.example.KweekCashLoanApp.dtos.responses.ClosedLoanResponse;
import com.example.KweekCashLoanApp.services.interfaces.IClosedLoanService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClosedLoanService implements IClosedLoanService {
    private final ClosedLoanRepository closedLoanRepository;

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
