package com.example.KweekCashLoanApp.controllers;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    ICustomerService customerService;
    @Autowired
    LoanOfficerController loanOfficerController;

    @PostMapping("/signUp")
    public String createAccount(@RequestBody RegisterUserRequest request){
        return customerService.registerCustomer(request).toString();
    }
    @PostMapping("/signIn")
    public String logIn(@RequestBody LoginRequest request){
        try {
        return customerService.logIn(request).getMessage();
        } catch (ObjectNotFoundException exception){
            log.info(exception.getMessage());
            return "User Not Found";
        }
    }

    @PostMapping("/applyForLoan")
    public String applyForALoan(@RequestBody LoanApplicationRequest request){
        return customerService.applyForALoan(request).toString();
    }
    @GetMapping("/checkLoanStatus")
    public String checkLoanStatus(@RequestBody LoanApplicationRequest request){
        return customerService.checkApplicationStatus(request).getMessage();
    }
    @GetMapping("/generateLoanAgreement")
    public String generateLoanAgreement(@RequestBody LoanApplicationRequest request){
//        return customerService.viewLoanAgreementForm(request).toString();
        return loanOfficerController.generateLoanAgreement(request);
    }
    @PutMapping("/updateDetails")
    public String editCustomerDetails(@RequestBody UpdateUserRequest request){
        return customerService.updateCustomerDetails(request).getMessage();
    }
    @PutMapping("/makePayment")
    public String makePayment(@RequestBody PaymentRequest request){
        return customerService.makePayment(request);
    }
    @GetMapping("/checkLoanBalance")
    public String checkLoanBalance(@RequestBody PaymentRequest request){
        return customerService.checkLoanBalance(request);
    }


}
