package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import com.example.KweekCashLoanApp.data.models.RejectedLoanRequests;
import com.example.KweekCashLoanApp.data.repositories.RejectedLoanRequestsRepository;
import com.example.KweekCashLoanApp.services.interfaces.IRejectedLoansService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.KweekCashLoanApp.utils.HardcodedValues.SUCCESSFULLY_SAVED;

@Service
@AllArgsConstructor
public class RejectedLoansService implements IRejectedLoansService {
    private final RejectedLoanRequestsRepository rejectedLoanRequestsRepository;

    @Override
    public String saveRejectedRequest(PendingLoanRequests foundRequest) {
        RejectedLoanRequests rejectedLoanRequests = new RejectedLoanRequests();
        BeanUtils.copyProperties(foundRequest,rejectedLoanRequests);
        rejectedLoanRequests.setMessage(foundRequest.getOptionalMessage());
        rejectedLoanRequestsRepository.save(rejectedLoanRequests);
        return SUCCESSFULLY_SAVED;
    }

    @Override
    public List<RejectedLoanRequests> findAllRejectedRequest() {
        return rejectedLoanRequestsRepository.findAll();
    }
}
