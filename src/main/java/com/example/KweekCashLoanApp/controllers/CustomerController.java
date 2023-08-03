package com.example.KweekCashLoanApp.controllers;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.LoginResponse;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final ICustomerService customerService;
    private final LoanOfficerController loanOfficerController;
    @Autowired
    public CustomerController(ICustomerService customerService,LoanOfficerController loanOfficerController){
        this.customerService = customerService;
        this.loanOfficerController = loanOfficerController;
    }

    @PostMapping("/signUp")
    public String createAccount(@RequestBody RegisterUserRequest request){
        try {
            return customerService.registerCustomer(request).toString();
        } catch (IncorrectDetailsException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/signIn")
    public String logIn(@RequestBody LoginRequest request){
        LoginResponse loginResponse = null;
        try {
            loginResponse = customerService.logIn(request);
        } catch (ObjectNotFoundException exception){
            log.info(exception.getMessage());

            loginResponse = new LoginResponse();
            loginResponse.setMessage(exception.getMessage());

        }
        return loginResponse.getMessage();
    }


    @PostMapping("/applyForLoan")
    public String applyForALoan(@RequestBody LoanApplicationRequest request){
        return customerService.applyForALoan(request).toString();
    }

    @GetMapping("/checkLoanStatus")
    public String checkLoanStatus(@RequestBody LoanApplicationRequest request){
        try {
            return customerService.checkApplicationStatus(request).getMessage();
        } catch (IncorrectDetailsException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/generateLoanAgreement")
    public String generateLoanAgreement(@RequestBody LoanApplicationRequest request){
        return loanOfficerController.generateLoanAgreement(request);
    }

    @PutMapping("/updateDetails")
    public String editCustomerDetails(@RequestBody UpdateUserRequest request){
        try {
            return customerService.updateCustomerDetails(request).getMessage();
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/makePayment")
    public String makePayment(@RequestBody PaymentRequest request){
        try {
            return customerService.makePayment(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/checkLoanBalance")
    public String checkLoanBalance(@RequestBody PaymentRequest request){
        try {
            return customerService.checkLoanBalance(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }





//    @PostMapping("/signUp")
//    public ResponseEntity<?> createAccount(@RequestBody RegisterUserRequest request){
//        try {
//            return new ResponseEntity<>(new ApiResponse(true, customerService.registerCustomer(request))
//                    , HttpStatus.FOUND);
//        } catch (IncorrectDetailsException exception) {
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage())
//                    , HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("/signIn")
//    public ResponseEntity<?> logIn(@RequestBody LoginRequest request){
//        try {
//            return new ResponseEntity<>(new ApiResponse(true, customerService.logIn(request))
//                    , HttpStatus.FOUND);
//        } catch (ObjectNotFoundException exception){
//            log.info(exception.getMessage());
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage())
//                    , HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("/applyForLoan")
//    public ResponseEntity<?> applyForALoan(@RequestBody LoanApplicationRequest request){
//        return new ResponseEntity<>(new ApiResponse(true, customerService.applyForALoan(request))
//                , HttpStatus.FOUND);
//    }
//    @GetMapping("/checkLoanStatus")
//    public ResponseEntity<?> checkLoanStatus(@RequestBody LoanApplicationRequest request){
//        try {
//            return new ResponseEntity<>(new ApiResponse(true, customerService.checkApplicationStatus(request))
//                    , HttpStatus.FOUND);
//        } catch (IncorrectDetailsException exception){
//            log.info(exception.getMessage());
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage())
//                    , HttpStatus.NOT_FOUND);
//        }
//    }
//    @GetMapping("/generateLoanAgreement")
//    public ResponseEntity<?> generateLoanAgreement(@RequestBody LoanApplicationRequest request) {
//        return new ResponseEntity<>(new ApiResponse(true, loanOfficerController.generateLoanAgreement(request))
//                , HttpStatus.FOUND);
//    }
//
//    @PutMapping("/updateDetails")
//    public ResponseEntity<?> editCustomerDetails(@RequestBody UpdateUserRequest request){
//        try {
//            return new ResponseEntity<>(new ApiResponse(true, customerService.updateCustomerDetails(request))
//                    , HttpStatus.FOUND);
//        } catch (ObjectNotFoundException exception){
//            log.info(exception.getMessage());
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage())
//                    , HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/makePayment")
//    public ResponseEntity<?> makePayment(@RequestBody PaymentRequest request){
//        try {
//            return new ResponseEntity<>(new ApiResponse(true, customerService.makePayment(request))
//                    , HttpStatus.FOUND);
//        } catch (ObjectNotFoundException exception){
//            log.info(exception.getMessage());
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage())
//                    , HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("/checkLoanBalance")
//    public ResponseEntity<?> checkLoanBalance(@RequestBody PaymentRequest request){
//        try {
//            return new ResponseEntity<>(new ApiResponse(true, customerService.checkLoanBalance(request))
//                    , HttpStatus.FOUND);
//        } catch (ObjectNotFoundException exception){
//            log.info(exception.getMessage());
//            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage())
//                    , HttpStatus.NOT_FOUND);
//        }
//    }



}
