package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.AppUtils;
import com.example.KweekCashLoanApp.Map;
import com.example.KweekCashLoanApp.data.models.ApprovedLoanRequests;
import com.example.KweekCashLoanApp.data.models.Customer;
import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import com.example.KweekCashLoanApp.data.repositories.PendingLoanRequestsRepository;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.requests.LoanUpdateRequest;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.LoanApplicationResponse;
import com.example.KweekCashLoanApp.dtos.responses.PendingLoanResponse;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.IActiveLoansService;
import com.example.KweekCashLoanApp.services.interfaces.IApprovedLoansService;
import com.example.KweekCashLoanApp.services.interfaces.IPendingLoansService;
import com.example.KweekCashLoanApp.services.interfaces.IRejectedLoansService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.KweekCashLoanApp.AppUtils.*;
import static com.example.KweekCashLoanApp.data.enums.LoanStatus.*;

@Service
public class PendingLoansService implements IPendingLoansService {
    @Autowired
    PendingLoanRequestsRepository pendingLoanRequestsRepository;
    @Autowired
    IApprovedLoansService approvedLoansService;
    @Autowired
    IRejectedLoansService rejectedLoansService;
    @Autowired
    IActiveLoansService activeLoansService;

    @Override
    public LoanApplicationResponse requestForALoan(LoanApplicationRequest request, Customer foundCustomer) {
        request.setCustomerId(foundCustomer.getCustomerId());
        request.setUniqueCode(AppUtils.generateUniqueCode(10));

        LoanApplicationResponse response = Map.loanReqToRes(request,foundCustomer);

        PendingLoanRequests loanRequest = new PendingLoanRequests();
        BeanUtils.copyProperties(request,loanRequest);

        loanRequest.setLoanStatus(AWAITING_APPROVAL);
        loanRequest.setLoanTenure(loanTenure(request));
        loanRequest.setRepaymentPreference(repaymentPreference(request));
        loanRequest.setPaymentMethod(paymentMethod(request));
        loanRequest.setDateOfApplication(LocalDate.now());

        pendingLoanRequestsRepository.save(loanRequest);
        return response;
    }
    @Override
    public LoanApplicationResponse confirmStatus(LoanApplicationRequest request) throws IncorrectDetailsException {
        PendingLoanRequests foundApplication = pendingLoanRequestsRepository.findLoanRequestsByUniqueCode(request.getUniqueCode());
        if(foundApplication == null)throw new IncorrectDetailsException("Incorrect Unique Code Entered");
        LoanApplicationResponse response = new LoanApplicationResponse();

        if(foundApplication.getLoanStatus() == AWAITING_APPROVAL){
            response.setMessage("Awaiting approval. Please check back in 48 hours.");
        } else if (foundApplication.getLoanStatus() == APPROVED) {
            response.setMessage("Application approved.\nPlease proceed to download and execute loan terms and conditions.");
        } else if (foundApplication.getLoanStatus() == REJECTED) {
            response.setMessage("Application Rejected.\nRemark: "+foundApplication.getOptionalMessage());
        }
        return response;
    }

    @Override
    public PendingLoanResponse findRequestById(Long id) throws ObjectNotFoundException {
        PendingLoanRequests foundRequest = pendingLoanRequestsRepository.findById(id).get();
        if(foundRequest == null){
            throw new ObjectNotFoundException("Loan Request Not Found");
        }

        PendingLoanResponse response = new PendingLoanResponse();
        BeanUtils.copyProperties(foundRequest,response);
        response.setOptionalMessage(foundRequest.getOptionalMessage());
        return response;
    }

    @Override
    public List<PendingLoanRequests> findAllPendingRequests() {
        return pendingLoanRequestsRepository.findAll();
    }

    @Override
    public String updateRequestDetails(LoanUpdateRequest request) throws ObjectNotFoundException {
        Optional<PendingLoanRequests> foundRequestOptional = pendingLoanRequestsRepository.findById(request.getLoanRequestId());
        PendingLoanRequests foundRequest = foundRequestOptional.orElseThrow(() -> new ObjectNotFoundException("Loan request not found"));

        foundRequest.setLoanStatus(request.getLoanStatus());
        foundRequest.setOptionalMessage(request.getMessage());

        if(foundRequest.getLoanStatus() == APPROVED){
            ApprovedLoanRequests approvedLoanRequests = new ApprovedLoanRequests();
            BeanUtils.copyProperties(foundRequest,approvedLoanRequests);

            approvedLoanRequests.setLoanStatus(loanStatus(foundRequest.getLoanStatus()));
            approvedLoanRequests.setInterestRate(request.getInterestRate());
            approvedLoanRequests.setStartDate(stringToLocalDate(request.getStartDate()));
            approvedLoanRequests.setEndDate(stringToLocalDate(request.getEndDate()));
            approvedLoanRequests.setAmountPerPaymentPeriod(amountToPay(foundRequest.getLoanAmount(),foundRequest.getLoanTenure(), request.getInterestRate()));
            approvedLoanRequests.setDateApproved(stringToLocalDate(request.getDateApproved()));
            ApprovedLoanResponse approvedLoan = approvedLoansService.saveApprovedRequest(approvedLoanRequests);

            activeLoansService.saveActiveLoans(approvedLoan);
                    //OR
//            activeLoansService.saveActiveLoans(approvedLoansService.saveApprovedRequest(approvedLoanRequests));

        } else if (foundRequest.getLoanStatus() == REJECTED) {
            rejectedLoansService.saveRejectedRequest(foundRequest);
        }

        return "Review Successful";
    }
}
