package com.example.KweekCashLoanApp.controllers;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.LoanApplicationResponse;
import com.example.KweekCashLoanApp.dtos.responses.LoginResponse;
import com.example.KweekCashLoanApp.dtos.responses.RegisterUserResponse;
import com.example.KweekCashLoanApp.dtos.responses.UpdateUserResponse;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.KweekCashLoanApp.utils.ApiValues.*;

@Slf4j
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping(CUSTOMER)
public class CustomerController {
    private final ICustomerService customerService;
    private final LoanOfficerController loanOfficerController;

    @PostMapping(SIGN_UP)
    public ResponseEntity<RegisterUserResponse> createAccount(@RequestBody RegisterUserRequest request){
        RegisterUserResponse response = customerService.registerCustomer(request);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping(SIGN_IN)
    public ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest request){
        LoginResponse loginResponse = customerService.logIn(request);
        return ResponseEntity.ok().body(loginResponse);
    }


    @PostMapping(APPLY_FOR_LOAN)
    public ResponseEntity<LoanApplicationResponse> applyForALoan(@RequestBody LoanApplicationRequest request){
        LoanApplicationResponse response = customerService.applyForALoan(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(CHECK_LOAN_STATUS)
    public ResponseEntity<LoanApplicationResponse> checkLoanStatus(@RequestBody LoanApplicationRequest request){
        LoanApplicationResponse response = customerService.checkApplicationStatus(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(GENERATE_LOAN_AGREEMENT)
    public ResponseEntity<String> generateLoanAgreement(@RequestBody LoanApplicationRequest request){
        return ResponseEntity.ok().body(loanOfficerController.generateLoanAgreement(request).toString());
    }

    @PutMapping(UPDATE_USER_DETAILS)
    public ResponseEntity<UpdateUserResponse> editCustomerDetails(@RequestBody UpdateUserRequest request){
        UpdateUserResponse response = customerService.updateCustomerDetails(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(MAKE_PAYMENT)
    public ResponseEntity<String> makePayment(@RequestBody PaymentRequest request){
        return ResponseEntity.ok().body(customerService.makePayment(request));
    }

    @GetMapping(CHECK_LOAN_BALANCE)
    public ResponseEntity<String> checkLoanBalance(@RequestBody PaymentRequest request){
        return ResponseEntity.ok().body(customerService.checkLoanBalance(request));
    }
}
