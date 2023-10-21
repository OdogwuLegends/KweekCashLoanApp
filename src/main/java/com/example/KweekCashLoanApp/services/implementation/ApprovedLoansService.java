package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.ApprovedLoanRequests;
import com.example.KweekCashLoanApp.data.repositories.ApprovedLoanRequestsRepository;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.IApprovedLoansService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.KweekCashLoanApp.utils.HardcodedValues.REQUEST_NOT_FOUND;

@Service
@AllArgsConstructor
public class ApprovedLoansService implements IApprovedLoansService {
     private final ApprovedLoanRequestsRepository approvedLoanRequestsRepository;

    @Override
    public ApprovedLoanResponse saveApprovedRequest(ApprovedLoanRequests requests) {
        ApprovedLoanRequests savedRequest = approvedLoanRequestsRepository.save(requests);

        ApprovedLoanResponse response = new ApprovedLoanResponse();
        BeanUtils.copyProperties(savedRequest,response);
        return response;
    }

    @Override
    public ApprovedLoanResponse findRequestByUniqueCode(LoanApplicationRequest request) throws ObjectNotFoundException {
        ApprovedLoanRequests approvedRequest = approvedLoanRequestsRepository.findByUniqueCode(request.getUniqueCode()).orElseThrow(()-> new ObjectNotFoundException(REQUEST_NOT_FOUND));

        ApprovedLoanResponse response = new ApprovedLoanResponse();
        BeanUtils.copyProperties(approvedRequest,response);
        return response;
    }

    @Override
    public List<ApprovedLoanRequests> findAllApprovedRequests() {
        return approvedLoanRequestsRepository.findAll();
    }
}
