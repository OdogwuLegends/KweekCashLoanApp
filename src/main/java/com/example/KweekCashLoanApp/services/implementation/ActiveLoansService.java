package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.ActiveLoans;
import com.example.KweekCashLoanApp.data.repositories.ActiveLoansRepository;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ClosedLoanResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.IActiveLoansService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.example.KweekCashLoanApp.data.enums.LoanStatus.IN_PROGRESS;
import static com.example.KweekCashLoanApp.utils.AppUtils.loanStatus;
import static com.example.KweekCashLoanApp.utils.AppUtils.messageForNewBalance;
import static com.example.KweekCashLoanApp.utils.HardcodedValues.*;

@Service
@AllArgsConstructor
@Slf4j
public class ActiveLoansService implements IActiveLoansService {
    private final ActiveLoansRepository activeLoansRepository;
    private final ClosedLoanService closedLoanService;

    @Override
    public ActiveLoanResponse saveActiveLoans(ApprovedLoanResponse response) {
        ActiveLoans activeLoans = new ActiveLoans();
        BeanUtils.copyProperties(response,activeLoans);
        activeLoans.setLoanStatus(loanStatus(IN_PROGRESS));
        activeLoans.setBalance(response.getLoanAmount());
        ActiveLoans savedLoan = activeLoansRepository.save(activeLoans);
        ActiveLoanResponse activeLoanResponse = new ActiveLoanResponse();
        BeanUtils.copyProperties(savedLoan,activeLoanResponse);
        return activeLoanResponse;
    }

    @Override
    public ActiveLoanResponse findByCustomerId(Long id) {
        ActiveLoans activeLoan = activeLoansRepository.findByCustomerId(id).orElseThrow(()-> new ObjectNotFoundException(REQUEST_NOT_FOUND));
        ActiveLoanResponse response = new ActiveLoanResponse();
        BeanUtils.copyProperties(activeLoan,response);
        return response;
    }

    @Override
    public List<ActiveLoans> findAllActiveLoans() {
        return activeLoansRepository.findAll();
    }

    @Override
    public String setNewBalance(Long customerId, BigDecimal amountPaid) throws ObjectNotFoundException {
        ActiveLoans activeLoan = activeLoansRepository.findByCustomerId(customerId).orElseThrow(()-> new ObjectNotFoundException(NO_LOAN_RUNNING_CURRENTLY));

        if(activeLoan.getLoanStatus().equals(ACTIVE_LOAN_IN_PROGRESS)){
            activeLoan.setTotalAmountRepaid(activeLoan.getTotalAmountRepaid().add(amountPaid));
            activeLoan.setBalance(activeLoan.getLoanAmount().subtract(activeLoan.getTotalAmountRepaid()));
            activeLoansRepository.save(activeLoan);
            return messageForNewBalance(amountPaid.toString(),activeLoan.getBalance().toString());

        } else if (activeLoan.getBalance().compareTo(activeLoan.getLoanAmount())>=0) {
            activeLoan.setLoanStatus(CLOSED);
            ClosedLoanResponse closedLoan = new ClosedLoanResponse();
            BeanUtils.copyProperties(activeLoan,closedLoan);
            closedLoanService.saveClosedLoan(closedLoan);
            return LOAN_COMPLETED;
        }
        return EMPTY_STRING;
    }

    @Override
    public ActiveLoanResponse getLoanDetails(Long customerId) throws ObjectNotFoundException {
        ActiveLoans activeLoan = activeLoansRepository.findByCustomerId(customerId).orElseThrow(()-> new ObjectNotFoundException(NO_LOAN_RUNNING_CURRENTLY));

        ActiveLoanResponse response = new ActiveLoanResponse();
        BeanUtils.copyProperties(activeLoan,response);
        return response;
    }
}
