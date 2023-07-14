package com.example.KweekCashLoanApp.controllers;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.PendingLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.RejectedLoanResponse;
import com.example.KweekCashLoanApp.services.ILoanOfficerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loanOfficer")
@Slf4j
public class LoanOfficerController {
    @Autowired
    ILoanOfficerService loanOfficerService;

    @PostMapping("/signUp")
    public String createLoanOfficerAccount(@RequestBody RegisterUserRequest request){
       return loanOfficerService.registerLoanOfficer(request).getMessage();
    }
    @PostMapping("/signIn")
    public String logIn(@RequestBody LoginRequest request) {
        return loanOfficerService.login(request).getMessage();
    }

    @PutMapping("/updateDetails")
    public String editDetails(@RequestBody UpdateUserRequest request){
        return loanOfficerService.updateLoanOfficerDetails(request).getMessage();
    }

    @GetMapping("/pendingRequests")
    public List<PendingLoanResponse> seePendingRequest(@RequestBody LoanUpdateRequest request){
        return loanOfficerService.seePendingLoanRequests(request);
    }
    @GetMapping("/approvedRequests")
    public List<ApprovedLoanResponse> seeApprovedRequests(@RequestBody LoanUpdateRequest request){
        return loanOfficerService.seeApprovedLoanRequests(request);
    }
    @GetMapping("/rejectedRequests")
    public List<RejectedLoanResponse> seeRejectedRequests(@RequestBody LoanUpdateRequest request){
        return loanOfficerService.seeRejectedLoanRequests(request);
    }
    @GetMapping("/activeLoans")
    public List<ActiveLoanResponse> seeActiveLoans(@RequestBody LoanUpdateRequest request){
        return loanOfficerService.seeActiveLoans(request);
    }
    @PostMapping("/reviewLoanRequest")
    public String reviewPendingRequests(@RequestBody LoanUpdateRequest request){
        return loanOfficerService.reviewLoanRequest(request);
    }
    @GetMapping("/generateLoanAgreement")
    public String generateLoanAgreement(@RequestBody LoanApplicationRequest request){
        return loanOfficerService.generateLoanAgreementForm(request).toString();
    }
}
