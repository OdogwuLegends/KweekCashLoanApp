package com.example.KweekCashLoanApp.controllers;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.ILoanOfficerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.KweekCashLoanApp.utils.ApiValues.*;

@RestController
@RequestMapping(LOAN_OFFICER)
@AllArgsConstructor
@Slf4j
public class LoanOfficerController {
    private final ILoanOfficerService loanOfficerService;

    @PostMapping(SIGN_UP)
    public ResponseEntity<RegisterUserResponse> createLoanOfficerAccount(@RequestBody RegisterUserRequest request){
        RegisterUserResponse response = loanOfficerService.registerLoanOfficer(request);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping(SIGN_IN)
    public ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loanOfficerService.login(request);
        return ResponseEntity.ok().body(loginResponse);
    }

    @PutMapping(UPDATE_USER_DETAILS)
    public ResponseEntity<UpdateUserResponse> editDetails(@RequestBody UpdateUserRequest request){
        UpdateUserResponse response = loanOfficerService.updateLoanOfficerDetails(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(PENDING_REQUESTS)
    public ResponseEntity<List<PendingLoanResponse>> seePendingRequest(@RequestBody LoanUpdateRequest request){
        List<PendingLoanResponse> pendingLoanResponseList = loanOfficerService.seePendingLoanRequests(request);
        return ResponseEntity.ok().body(pendingLoanResponseList);
    }
    @GetMapping(APPROVED_REQUESTS)
    public ResponseEntity<List<ApprovedLoanResponse>> seeApprovedRequests(@RequestBody LoanUpdateRequest request){
        List<ApprovedLoanResponse> approvedLoanResponseList = loanOfficerService.seeApprovedLoanRequests(request);
        return ResponseEntity.ok().body(approvedLoanResponseList);
    }
    @GetMapping(REJECTED_REQUESTS)
    public ResponseEntity<List<RejectedLoanResponse>> seeRejectedRequests(@RequestBody LoanUpdateRequest request){
        List<RejectedLoanResponse> rejectedLoanResponseList = loanOfficerService.seeRejectedLoanRequests(request);
        return ResponseEntity.ok().body(rejectedLoanResponseList);
    }
    @GetMapping(ACTIVE_LOANS)
    public ResponseEntity<List<ActiveLoanResponse>> seeActiveLoans(@RequestBody LoanUpdateRequest request){
        List<ActiveLoanResponse> activeLoanResponseList = loanOfficerService.seeActiveLoans(request);
        return ResponseEntity.ok().body(activeLoanResponseList);
    }
    @PostMapping(REVIEW_LOAN_REQUEST)
    public ResponseEntity<String> reviewPendingRequests(@RequestBody LoanUpdateRequest request){
        return ResponseEntity.ok().body(loanOfficerService.reviewLoanRequest(request));
    }
    @GetMapping(GENERATE_LOAN_AGREEMENT)
    public ResponseEntity<String> generateLoanAgreement(@RequestBody LoanApplicationRequest request){
        return ResponseEntity.ok().body(loanOfficerService.generateLoanAgreementForm(request).toString());
    }
}
