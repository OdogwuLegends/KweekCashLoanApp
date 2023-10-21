package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.*;
import com.example.KweekCashLoanApp.data.repositories.LoanOfficerRepository;
import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.IActiveLoansService;
import com.example.KweekCashLoanApp.services.interfaces.IApprovedLoansService;
import com.example.KweekCashLoanApp.services.interfaces.ILoanOfficerService;
import com.example.KweekCashLoanApp.services.interfaces.IRejectedLoansService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.KweekCashLoanApp.data.enums.LoanStatus.AWAITING_APPROVAL;
import static com.example.KweekCashLoanApp.utils.AppUtils.*;
import static com.example.KweekCashLoanApp.utils.HardcodedValues.*;

@Slf4j
@Service
@AllArgsConstructor
public class LoanOfficerService implements ILoanOfficerService {
    private final LoanOfficerRepository loanOfficerRepository;
    private final PendingLoansService pendingLoansService;
    private final CustomerService customerService;
    private final IApprovedLoansService approvedLoansService;
    private final IRejectedLoansService rejectedLoansService;
    private final IActiveLoansService activeLoansService;

    @Override
    public RegisterUserResponse registerLoanOfficer(RegisterUserRequest request) throws IncorrectDetailsException {
        validateDetails(request);

        LoanOfficer newLoanOfficer = buildNewLoanOfficer(request);

        LoanOfficer savedLoanOfficer = loanOfficerRepository.save(newLoanOfficer);
        RegisterUserResponse response = new RegisterUserResponse();
        BeanUtils.copyProperties(savedLoanOfficer,response);

        response.setMessage(messageForNewLoanOfficer(savedLoanOfficer.getFirstName(),savedLoanOfficer.getLastName(),
                savedLoanOfficer.getAdminLoginCode(),savedLoanOfficer.getAuthorizationCode()));
        return response;
    }

    @Override
    public UpdateUserResponse updateLoanOfficerDetails(UpdateUserRequest request) throws ObjectNotFoundException {
        LoanOfficer foundLoanOfficer = loanOfficerRepository.findLoanOfficerByEmail(request.getEmail()).orElseThrow(()-> new ObjectNotFoundException(EMAIL_NOT_CORRECT));

        if(Objects.nonNull(request.getFirstName()) && !request.getFirstName().equals(EMPTY_STRING))foundLoanOfficer.setFirstName(request.getFirstName().toUpperCase());
        if(Objects.nonNull(request.getLastName()) && !request.getLastName().equals(EMPTY_STRING))foundLoanOfficer.setLastName(request.getLastName().toUpperCase());
        if(Objects.nonNull(request.getNewEmail()) && !request.getNewEmail().equals(EMPTY_STRING))foundLoanOfficer.setEmail(request.getNewEmail());
        if(Objects.nonNull(request.getNewPassword()) && !request.getNewPassword().equals(EMPTY_STRING))foundLoanOfficer.setPassword(request.getNewPassword());
        if(Objects.nonNull(request.getPhoneNumber()) && !request.getPhoneNumber().equals(EMPTY_STRING))foundLoanOfficer.setPhoneNumber(request.getPhoneNumber());
        if(Objects.nonNull(request.getStreetNumber()) && !request.getStreetNumber().equals(EMPTY_STRING))foundLoanOfficer.setStreetNumber(request.getStreetNumber().toUpperCase());
        if(Objects.nonNull(request.getStreetName()) && !request.getStreetName().equals(EMPTY_STRING))foundLoanOfficer.setStreetName(request.getStreetName().toUpperCase());
        if(Objects.nonNull(request.getTown()) && !request.getTown().equals(EMPTY_STRING))foundLoanOfficer.setTown(request.getTown().toUpperCase());
        if(Objects.nonNull(request.getState()) && !request.getState().equals(EMPTY_STRING))foundLoanOfficer.setState(request.getState().toUpperCase());

        LoanOfficer updatedOfficer = loanOfficerRepository.save(foundLoanOfficer);

        UpdateUserResponse response = new UpdateUserResponse();
        BeanUtils.copyProperties(updatedOfficer,response);
        response.setMessage(UPDATE_SUCCESSFUL);
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws ObjectNotFoundException {
        LoanOfficer foundOfficer = loanOfficerRepository.findLoanOfficerByEmail(request.getEmail()).orElseThrow(()-> new ObjectNotFoundException(EMAIL_NOT_CORRECT));

        LoginResponse response = new LoginResponse();
        if(!foundOfficer.getPassword().equals(request.getPassword())){
            throw new ObjectNotFoundException(PASSWORD_NOT_CORRECT);
        } else if (!foundOfficer.getAdminLoginCode().equals(request.getAdminLoginCode())) {
            throw new ObjectNotFoundException(ADMIN_LOGIN_CODE_NOT_CORRECT);
        } else {
            response.setLoggedIn(true);
            response.setMessage(LOG_IN_SUCCESSFUL);
        }
        return response;
    }

    @Override
    public List<PendingLoanResponse> seePendingLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException {
        findAdminByLoginCode(request);

        List<PendingLoanRequests> allRequests = pendingLoansService.findAllPendingRequests();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException(NO_PENDING_LOAN_REQUESTS);

        List<PendingLoanResponse> response = new ArrayList<>();

        for (PendingLoanRequests allRequest : allRequests) {
            if(allRequest.getLoanStatus() == AWAITING_APPROVAL){
                PendingLoanResponse loanResponse = new PendingLoanResponse();
                BeanUtils.copyProperties(allRequest, loanResponse);
                FindUserResponse foundCustomer = new FindUserResponse();
                try {
                    foundCustomer = customerService.findCustomerById(allRequest.getCustomerId());
                }catch (ObjectNotFoundException ex){
                    log.info(ex.getMessage());
                }

                loanResponse.setLoanStatus(loanStatus(allRequest.getLoanStatus()));
                loanResponse.setFirstName(foundCustomer.getFirstName());
                loanResponse.setLastName(foundCustomer.getLastName());
                loanResponse.setAddress(foundCustomer.getAddress());
                loanResponse.setOccupation(foundCustomer.getOccupation());
                response.add(loanResponse);
            }
        }
        if(response.isEmpty()) throw new ObjectNotFoundException(NO_PENDING_LOAN_REQUESTS);
        return response;
    }

    @Override
    public List<ApprovedLoanResponse> seeApprovedLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException {
        findAdminByLoginCode(request);

        List<ApprovedLoanRequests> allRequests = approvedLoansService.findAllApprovedRequests();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException(NO_APPROVED_LOAN_REQUESTS);

        List<ApprovedLoanResponse> approvedLoanResponses = new ArrayList<>();

        for (ApprovedLoanRequests eachRequest: allRequests) {
            ApprovedLoanResponse response = new ApprovedLoanResponse();
            BeanUtils.copyProperties(eachRequest,response);
            approvedLoanResponses.add(response);

        }
        return approvedLoanResponses;
    }

    @Override
    public List<ActiveLoanResponse> seeActiveLoans(LoanUpdateRequest request) throws ObjectNotFoundException {
        findAdminByLoginCode(request);
        List<ActiveLoans> allRequests = activeLoansService.findAllActiveLoans();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException(NO_ACTIVE_LOANS);

        List<ActiveLoanResponse> activeLoanResponse = new ArrayList<>();

        for (ActiveLoans eachLoan: allRequests) {
            ActiveLoanResponse response = new ActiveLoanResponse();
            BeanUtils.copyProperties(eachLoan,response);
            activeLoanResponse.add(response);
        }
        return activeLoanResponse;
    }

    private void findAdminByLoginCode(LoanUpdateRequest request) {
        loanOfficerRepository.findLoanOfficerByAdminLoginCode(request.getAdminLoginCode()).orElseThrow(()-> new ObjectNotFoundException(ADMIN_LOGIN_CODE_NOT_CORRECT));
    }

    @Override
    public List<RejectedLoanResponse> seeRejectedLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException {
        findAdminByLoginCode(request);

        List<RejectedLoanRequests> allRequests = rejectedLoansService.findAllRejectedRequest();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException(NO_REJECTED_LOAN_REQUESTS);

        List<RejectedLoanResponse> rejectedLoanResponses = new ArrayList<>();

        for(RejectedLoanRequests eachRequest : allRequests){
            RejectedLoanResponse response = new RejectedLoanResponse();
            BeanUtils.copyProperties(eachRequest,response);
            rejectedLoanResponses.add(response);
        }
        return rejectedLoanResponses;
    }

    @Override
    public String reviewLoanRequest(LoanUpdateRequest request) throws ObjectNotFoundException {
        loanOfficerRepository.findLoanOfficerByAdminLoginCodeAndAuthorizationCode(request.getAdminLoginCode(),request.getAuthorizationCode()).orElseThrow(()-> new ObjectNotFoundException(INCORRECT_LOGIN_DETAILS));
        return pendingLoansService.updateRequestDetails(request);
    }

    @Override
    public LoanAgreementResponse generateLoanAgreementForm(LoanApplicationRequest request) throws ObjectNotFoundException {
        ApprovedLoanResponse approvedLoan = approvedLoansService.findRequestByUniqueCode(request);
        if(!Objects.equals(approvedLoan.getLoanStatus(), APPROVED_STATUS )) throw new ObjectNotFoundException(REQUEST_NOT_APPROVED);

//        FindUserResponse foundCustomer = customerService.findCustomerById(approvedLoan.getCustomerId());

        FindUserResponse foundCustomer = new FindUserResponse();
        try {
            foundCustomer = customerService.findCustomerById(approvedLoan.getCustomerId());
        }catch (ObjectNotFoundException ex){
            log.info(ex.getMessage());
        }

        LoanAgreementResponse response = new LoanAgreementResponse();
        BeanUtils.copyProperties(approvedLoan,response);
        response.setFirstName(foundCustomer.getFirstName());
        response.setLastName(foundCustomer.getLastName());
        response.setEmail(foundCustomer.getEmail());
        response.setPhoneNumber(foundCustomer.getPhoneNumber());
        response.setAddress(foundCustomer.getAddress());

        return response;
    }

    private static LoanOfficer buildNewLoanOfficer(RegisterUserRequest request) {
        LoanOfficer loanOfficer = new LoanOfficer();

        loanOfficer.setFirstName(request.getFirstName().toUpperCase());
        loanOfficer.setLastName(request.getLastName().toUpperCase());
        loanOfficer.setEmail(request.getEmail());
        loanOfficer.setPassword(request.getPassword());
        loanOfficer.setPhoneNumber(request.getPhoneNumber());
        loanOfficer.setStreetNumber(request.getStreetNumber());
        loanOfficer.setStreetName(request.getStreetName().toUpperCase());
        loanOfficer.setTown(request.getTown().toUpperCase());
        loanOfficer.setState(request.getState().toUpperCase());
        loanOfficer.setDateRegistered(LocalDate.now());
        loanOfficer.setAdminLoginCode(generateUniqueCode(10));
        loanOfficer.setAuthorizationCode(generateRandomNumbers(10));

        return loanOfficer;
    }
}
