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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.KweekCashLoanApp.AppUtils.*;
import static com.example.KweekCashLoanApp.data.enums.LoanStatus.AWAITING_APPROVAL;

@Slf4j
@Service
public class LoanOfficerService implements ILoanOfficerService {
    private LoanOfficerRepository loanOfficerRepository;
    private PendingLoansService pendingLoansService;
    private CustomerService customerService;
    private IApprovedLoansService approvedLoansService;
    private IRejectedLoansService rejectedLoansService;
    private IActiveLoansService activeLoansService;

    @Autowired
    public LoanOfficerService(LoanOfficerRepository loanOfficerRepository,PendingLoansService pendingLoansService,
                              CustomerService customerService, IApprovedLoansService approvedLoansService,
                              IRejectedLoansService rejectedLoansService,IActiveLoansService activeLoansService
                                ){
        this.loanOfficerRepository = loanOfficerRepository;
        this.activeLoansService = activeLoansService;
        this.customerService = customerService;
        this.rejectedLoansService = rejectedLoansService;
        this.pendingLoansService = pendingLoansService;
        this.approvedLoansService = approvedLoansService;
    }


    @Override
    public RegisterUserResponse registerLoanOfficer(RegisterUserRequest request) throws IncorrectDetailsException {
        validateDetails(request);

        LoanOfficer newLoanOfficer = new LoanOfficer();
        BeanUtils.copyProperties(request,newLoanOfficer);

        newLoanOfficer.setDateRegistered(LocalDate.now());
        newLoanOfficer.setAdminLoginCode(generateUniqueCode(10));
        newLoanOfficer.setAuthorizationCode(generateRandomNumbers(10));

        LoanOfficer savedLoanOfficer = loanOfficerRepository.save(newLoanOfficer);
        RegisterUserResponse response = new RegisterUserResponse();
        BeanUtils.copyProperties(savedLoanOfficer,response);

        response.setMessage("Welcome "+savedLoanOfficer.getFirstName()+" "+savedLoanOfficer.getLastName()+
                ". Your account creation was successful.\n"+
                "Your Admin Log In Code is "+savedLoanOfficer.getAdminLoginCode()+"\n"+
                "Your Authorization Code is "+savedLoanOfficer.getAuthorizationCode());
        return response;
    }

    @Override
    public UpdateUserResponse updateLoanOfficerDetails(UpdateUserRequest request) throws ObjectNotFoundException {
        LoanOfficer foundLoanOfficer = loanOfficerRepository.findLoanOfficerByEmail(request.getEmail());

        if(Objects.isNull(foundLoanOfficer)){
            throw new ObjectNotFoundException("Email not correct");
        }
        if(Objects.nonNull(request.getFirstName()) && !request.getFirstName().equals(""))foundLoanOfficer.setFirstName(request.getFirstName());
        if(Objects.nonNull(request.getLastName()) && !request.getLastName().equals(""))foundLoanOfficer.setLastName(request.getLastName());
        if(Objects.nonNull(request.getNewEmail()) && !request.getNewEmail().equals(""))foundLoanOfficer.setEmail(request.getNewEmail());
        if(Objects.nonNull(request.getNewPassword()) && !request.getNewPassword().equals(""))foundLoanOfficer.setPassword(request.getNewPassword());
        if(Objects.nonNull(request.getPhoneNumber()) && !request.getPhoneNumber().equals(""))foundLoanOfficer.setPhoneNumber(request.getPhoneNumber());
        if(Objects.nonNull(request.getStreetNumber()) && !request.getStreetNumber().equals(""))foundLoanOfficer.setStreetNumber(request.getStreetNumber());
        if(Objects.nonNull(request.getStreetName()) && !request.getStreetName().equals(""))foundLoanOfficer.setStreetName(request.getStreetName());
        if(Objects.nonNull(request.getTown()) && !request.getTown().equals(""))foundLoanOfficer.setTown(request.getTown());
        if(Objects.nonNull(request.getState()) && !request.getState().equals(""))foundLoanOfficer.setState(request.getState());

        LoanOfficer updatedOfficer = loanOfficerRepository.save(foundLoanOfficer);

        UpdateUserResponse response = new UpdateUserResponse();
        BeanUtils.copyProperties(updatedOfficer,response);
        response.setMessage("Update successful");
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws ObjectNotFoundException {
        LoanOfficer foundOfficer = loanOfficerRepository.findLoanOfficerByEmail(request.getEmail());
        if(foundOfficer == null){
            throw new ObjectNotFoundException("Email not correct");
        }
        LoginResponse response = new LoginResponse();
        if(!foundOfficer.getPassword().equals(request.getPassword())){
            throw new ObjectNotFoundException("Password Incorrect");
        } else if (!foundOfficer.getAdminLoginCode().equals(request.getAdminLoginCode())) {
            throw new ObjectNotFoundException("Incorrect Log In Code");
        } else {
            response.setLoggedIn(true);
            response.setMessage("Log in successful");
        }
        return response;
    }

    @Override
    public List<PendingLoanResponse> seePendingLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException {
        loanOfficerRepository.findLoanOfficerByAdminLoginCode(request.getAdminLoginCode()).orElseThrow(()-> new ObjectNotFoundException("Incorrect Admin Log in Code"));

        List<PendingLoanRequests> allRequests = pendingLoansService.findAllPendingRequests();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException("No Pending Loan Requests");

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
        if(response.isEmpty()) throw new ObjectNotFoundException("No Pending Loan Requests found");
        return response;
    }

    @Override
    public List<ApprovedLoanResponse> seeApprovedLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException {
        loanOfficerRepository.findLoanOfficerByAdminLoginCode(request.getAdminLoginCode()).orElseThrow(()-> new ObjectNotFoundException("Incorrect Admin Log in Code"));

        List<ApprovedLoanRequests> allRequests = approvedLoansService.findAllApprovedRequests();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException("No Approved Loan Requests");

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
        loanOfficerRepository.findLoanOfficerByAdminLoginCode(request.getAdminLoginCode()).orElseThrow(()-> new ObjectNotFoundException("Incorrect Admin Log in Code"));
        List<ActiveLoans> allRequests = activeLoansService.findAllActiveLoans();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException("No Active Loans");

        List<ActiveLoanResponse> activeLoanResponse = new ArrayList<>();

        for (ActiveLoans eachLoan: allRequests) {
            ActiveLoanResponse response = new ActiveLoanResponse();
            BeanUtils.copyProperties(eachLoan,response);
            activeLoanResponse.add(response);
        }
        return activeLoanResponse;
    }

    @Override
    public List<RejectedLoanResponse> seeRejectedLoanRequests(LoanUpdateRequest request) throws ObjectNotFoundException {
        System.out.println("Request "+request.getAdminLoginCode());
        log.info("Request "+request.getAdminLoginCode());
        loanOfficerRepository.findLoanOfficerByAdminLoginCode(request.getAdminLoginCode()).orElseThrow(()-> new ObjectNotFoundException("Incorrect Admin Log in Code"));

        List<RejectedLoanRequests> allRequests = rejectedLoansService.findAllRejectedRequest();
        if(allRequests.isEmpty()) throw new ObjectNotFoundException("No Rejected Loan Requests");

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
        loanOfficerRepository.findLoanOfficerByAdminLoginCodeAndAuthorizationCode(request.getAdminLoginCode(),request.getAuthorizationCode()).orElseThrow(()-> new ObjectNotFoundException("Incorrect details"));
        return pendingLoansService.updateRequestDetails(request);
    }

    @Override
    public LoanAgreementResponse generateLoanAgreementForm(LoanApplicationRequest request) throws ObjectNotFoundException {
        ApprovedLoanResponse approvedLoan = approvedLoansService.findRequestByUniqueCode(request);
        if(!Objects.equals(approvedLoan.getLoanStatus(), "APPROVED")) throw new ObjectNotFoundException("REQUEST NOT APPROVED");

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

}
