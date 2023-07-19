package com.example.KweekCashLoanApp.controllers;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.PendingLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.RejectedLoanResponse;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.ILoanOfficerService;
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
        try {
            return loanOfficerService.registerLoanOfficer(request).getMessage();
        } catch (IncorrectDetailsException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/signIn")
    public String logIn(@RequestBody LoginRequest request) {
        try {
            return loanOfficerService.login(request).getMessage();
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/updateDetails")
    public String editDetails(@RequestBody UpdateUserRequest request){
        try {
            return loanOfficerService.updateLoanOfficerDetails(request).getMessage();
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/pendingRequests")
    public List<PendingLoanResponse> seePendingRequest(@RequestBody LoanUpdateRequest request){
        try {
            return loanOfficerService.seePendingLoanRequests(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/approvedRequests")
    public List<ApprovedLoanResponse> seeApprovedRequests(@RequestBody LoanUpdateRequest request){
        try {
            return loanOfficerService.seeApprovedLoanRequests(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/rejectedRequests")
    public List<RejectedLoanResponse> seeRejectedRequests(@RequestBody LoanUpdateRequest request){
        try {
            return loanOfficerService.seeRejectedLoanRequests(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/activeLoans")
    public List<ActiveLoanResponse> seeActiveLoans(@RequestBody LoanUpdateRequest request){
        try {
            return loanOfficerService.seeActiveLoans(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/reviewLoanRequest")
    public String reviewPendingRequests(@RequestBody LoanUpdateRequest request){
        try {
            return loanOfficerService.reviewLoanRequest(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/generateLoanAgreement")
    public String generateLoanAgreement(@RequestBody LoanApplicationRequest request){
        try {
            return loanOfficerService.generateLoanAgreementForm(request).toString();
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
