package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.data.models.ApprovedLoanRequests;
import com.example.KweekCashLoanApp.data.repositories.ApprovedLoanRequestsRepository;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovedLoansService implements IApprovedLoansService{

    @Autowired
    ApprovedLoanRequestsRepository approvedLoanRequestsRepository;
    @Override
    public ApprovedLoanResponse saveApprovedRequest(ApprovedLoanRequests requests) {
        ApprovedLoanRequests savedRequest = approvedLoanRequestsRepository.save(requests);

        ApprovedLoanResponse response = new ApprovedLoanResponse();
        BeanUtils.copyProperties(savedRequest,response);
        return response;
    }

    @Override
    public ApprovedLoanResponse findRequestByUniqueCode(LoanApplicationRequest request) {
        ApprovedLoanRequests approvedRequest = approvedLoanRequestsRepository.findByUniqueCode(request.getUniqueCode());
        ApprovedLoanResponse response = new ApprovedLoanResponse();
        BeanUtils.copyProperties(approvedRequest,response);
        return response;
    }

    @Override
    public List<ApprovedLoanRequests> findAllApprovedRequests() {
        return approvedLoanRequestsRepository.findAll();
    }
}
