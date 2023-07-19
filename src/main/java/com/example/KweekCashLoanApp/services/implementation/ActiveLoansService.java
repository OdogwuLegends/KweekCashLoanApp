package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.models.ActiveLoans;
import com.example.KweekCashLoanApp.data.repositories.ActiveLoansRepository;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ClosedLoanResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.example.KweekCashLoanApp.AppUtils.loanStatus;
import static com.example.KweekCashLoanApp.data.enums.LoanStatus.IN_PROGRESS;

@Service
@Slf4j
public class ActiveLoansService implements IActiveLoansService{
    @Autowired
    ActiveLoansRepository activeLoansRepository;
    @Autowired
    ClosedLoanService closedLoanService;
    @Override
    public ActiveLoanResponse saveActiveLoans(ApprovedLoanResponse response) {
        ActiveLoans activeLoans = new ActiveLoans();
        BeanUtils.copyProperties(response,activeLoans);
//        activeLoans.setUniqueCode(response.getUniqueCode());
        activeLoans.setLoanStatus(loanStatus(IN_PROGRESS));
        activeLoans.setBalance(response.getLoanAmount());
        ActiveLoans savedLoan = activeLoansRepository.save(activeLoans);
        ActiveLoanResponse activeLoanResponse = new ActiveLoanResponse();
        BeanUtils.copyProperties(savedLoan,activeLoanResponse);
        return activeLoanResponse;
    }

    @Override
    public ActiveLoanResponse findByCustomerId(Long id) {
        ActiveLoans activeLoan = activeLoansRepository.findByCustomerId(id);
        if(activeLoan == null) throw new RuntimeException("Request Not Found");
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
        ActiveLoans activeLoan = activeLoansRepository.findByCustomerId(customerId);
        if(activeLoan == null) throw new ObjectNotFoundException("Request Not Found");
        if(activeLoan.getLoanStatus().equals("IN PROGRESS")){
            activeLoan.setTotalAmountRepaid(activeLoan.getTotalAmountRepaid().add(amountPaid));
            activeLoan.setBalance(activeLoan.getLoanAmount().subtract(activeLoan.getTotalAmountRepaid()));
            activeLoansRepository.save(activeLoan);
            return "Payment of N"+amountPaid+" was successful"+
                    "\nYour current loan balance is N"+activeLoan.getBalance();

        } else if (activeLoan.getBalance().compareTo(activeLoan.getLoanAmount())>=0) {
            activeLoan.setLoanStatus("CLOSED");
            ClosedLoanResponse closedLoan = new ClosedLoanResponse();
            BeanUtils.copyProperties(activeLoan,closedLoan);
            closedLoanService.saveClosedLoan(closedLoan);
            return "Loan Completed";
        }
        return "";
    }

    @Override
    public ActiveLoanResponse getLoanDetails(Long customerId) {
        ActiveLoans activeLoan = activeLoansRepository.findByCustomerId(customerId);
        if(activeLoan == null) throw new RuntimeException("Request Not Found");

        ActiveLoanResponse response = new ActiveLoanResponse();
        BeanUtils.copyProperties(activeLoan,response);
        return response;
    }
}
