package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.ICustomerService;
import com.example.KweekCashLoanApp.services.interfaces.ILoanOfficerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.example.KweekCashLoanApp.services.implementation.TestVariables.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ILoanOfficerService loanOfficerService;

    @Test
    public void testCustomerServiceNotNull(){
        assertNotNull(customerService);
    }
    @Test
    void testToRegisterCustomerWithCorrectDetails(){
        RegisterUserRequest request = buildFavourBlack();

        RegisterUserResponse savedCustomer;

        savedCustomer = customerService.registerCustomer(request);

        assertEquals("Welcome "+savedCustomer.getFirstName() + " "+savedCustomer.getLastName()+". Your account creation was successful.",savedCustomer.toString());
    }


    @Test
    void testToRegisterCustomerWithIncorrectEmailFormatThrowsException(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Chinedu");
        request.setLastName("Ugbo");
        request.setEmail("chiboygmail.com");
        request.setPassword("Hello@chiBoy15");
        request.setOccupation("Software engineer");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");

        assertThrows(IncorrectDetailsException.class,() -> customerService.registerCustomer(request));
    }

    @Test
    void testToRegisterCustomerWithWrongPasswordFormatThrowsException(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Chinedu");
        request.setLastName("Ugbo");
        request.setEmail("chiboygmail@com");
        request.setPassword("Hello");
        request.setOccupation("Software engineer");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");

        assertThrows(IncorrectDetailsException.class,() -> customerService.registerCustomer(request));
    }

    @Test
    void testLoginWithCorrectDetails(){
        RegisterUserRequest request = buildFavourWhite();

        RegisterUserResponse savedCustomer;

        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);
        assertEquals("Welcome "+savedCustomer.getFirstName() + " "+savedCustomer.getLastName()+". Your account creation was successful.",savedCustomer.toString());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("favsam@gmail.com");
        loginRequest.setPassword("12BigWhiteFav@");

        LoginResponse response;
        response = customerService.logIn(loginRequest);
        assertNotNull(response);
        assertTrue(response.isLoggedIn());
    }
    @Test
    void testLoginWithWrongEmail(){
        LoginRequest request = new LoginRequest();
        request.setEmail("mywoman@gmail.com");
        request.setPassword("MyLeggy@1#");

        assertThrows(ObjectNotFoundException.class,()->customerService.logIn(request));
    }
    @Test
    void testLoginWithWrongPassword(){
        LoginRequest request = new LoginRequest();
        request.setEmail("legend@gmail.com");
        request.setPassword("myEverything98");

        assertThrows(ObjectNotFoundException.class,()->customerService.logIn(request));
    }
    @Test
    void testFindCustomerByCorrectId(){
        RegisterUserRequest request = buildLegend();

        RegisterUserResponse savedCustomer;

        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        FindUserResponse response;
        response = customerService.findCustomerById(savedCustomer.getCustomerId());

        assertNotNull(response);
        assertEquals("ODOGWU",response.getLastName());
    }
    @Test
    void testFindCustomerWithWrongId(){
        assertThrows(ObjectNotFoundException.class, ()-> customerService.findCustomerById(150));
    }
    @Test
    void testFindCustomerWithCorrectEmail(){
        RegisterUserRequest request = buildDominic();

        RegisterUserResponse savedCustomer;

        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        FindUserResponse response;

        response = customerService.findCustomerByEmail("domdom@gmail.com");

        assertNotNull(response);
        assertEquals("BANKING",response.getOccupation());
        assertEquals("SZOBOSZLAI",response.getLastName());
    }
    @Test
    void testFindCustomerWithWrongEmail(){
        assertThrows(ObjectNotFoundException.class,()-> customerService.findCustomerByEmail("cephas@gmail.com"));
    }
    @Test
    void testUpdateCustomerDetailsWithCorrectLoginEmail(){
        RegisterUserRequest request = buildInem();

        RegisterUserResponse savedCustomer;

        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);
        assertEquals("INEM", savedCustomer.getFirstName());

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("ename@gmail.com");
        updateUserRequest.setFirstName("Inemesit");

        UpdateUserResponse updatedResponse;
        updatedResponse = customerService.updateCustomerDetails(updateUserRequest);

        assertNotNull(updatedResponse);
        assertEquals("INEMESIT",updatedResponse.getFirstName());
    }
    @Test
    void testUpdateCustomerDetailsWithWrongLoginEmail(){
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("blackman@gmail.com");
        request.setFirstName("Bernado");

        assertThrows(ObjectNotFoundException.class,()-> customerService.updateCustomerDetails(request));
    }
    @Test
    void testApplyForLoan(){
        RegisterUserRequest request = buildMark();

        RegisterUserResponse savedCustomer;

        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForMark();

        LoanApplicationResponse response = customerService.applyForALoan(loanApplicationRequest);
        assertNotNull(response);
        assertEquals("MARK",response.getFirstName());
        assertEquals(BigDecimal.valueOf(500000),response.getLoanAmount());
    }

    @Test
    void testCheckLoanApplicationStatus(){
        RegisterUserRequest request = buildCephas();
        RegisterUserResponse savedCustomer;
        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForCephas();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);

        LoanApplicationRequest uniqueCode = new LoanApplicationRequest();
        uniqueCode.setUniqueCode(loanApplicationResponse.getUniqueCode());

        LoanApplicationResponse response;
        response = customerService.checkApplicationStatus(uniqueCode);

        assertEquals("Awaiting approval. Please check back in 48 hours.",response.getMessage());
    }
    @Test
    void testCheckBalanceOnActiveLoan(){
        RegisterUserRequest request = buildTimi();
        RegisterUserResponse savedCustomer;
        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request = buildSecondLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForTimi();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);
        Long loanRequestId = loanApplicationResponse.getLoanRequestId();

        LoanUpdateRequest loanUpdateRequest = buildReviewToApproveRequest(loanOfficer, loanRequestId);

        loanOfficerService.reviewLoanRequest(loanUpdateRequest);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setEmail("timi@gmail.com");

        String balance;
        balance = customerService.checkLoanBalance(paymentRequest);

        assertEquals("\t\t\t\t\t\t\t\t\tLOAN DETAILS\n" +
                "\n" +
                "LOAN AMOUNT -> 500000.00\n" +
                "TOTAL AMOUNT REPAID -> 0.00\n" +
                "BALANCE -> 500000.00\n" +
                "START DATE -> 2023-07-15\n" +
                "END DATE -> 2027-06-30",balance);
    }
    @Test
    void testCheckBalanceWithoutActiveLoan(){
        PaymentRequest request = new PaymentRequest();
        request.setEmail("legend@gmail.com");

        assertThrows(ObjectNotFoundException.class,()-> customerService.checkLoanBalance(request));
    }
    @Test
    void testMakePaymentOnActiveLoan(){
        RegisterUserRequest request = buildDavid();
        RegisterUserResponse savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request = buildFirstLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForDavid();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);
        Long loanRequestId = loanApplicationResponse.getLoanRequestId();

        LoanUpdateRequest loanUpdateRequest = buildReviewToApproveRequest(loanOfficer, loanRequestId);

        loanOfficerService.reviewLoanRequest(loanUpdateRequest);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setEmail("david@gmail.com");
        paymentRequest.setAmount(BigDecimal.valueOf(20000));

        String loanBalance = customerService.makePayment(paymentRequest);

        assertEquals( "Payment of N"+20000+" was successful"+
                "\nYour current loan balance is N480000.00",loanBalance);
    }

    @Test
    void testMakePaymentWithoutActiveLoan(){
        PaymentRequest request = new PaymentRequest();
        request.setEmail("legend@gmail.com");
        request.setAmount(BigDecimal.valueOf(20000));

        assertThrows(ObjectNotFoundException.class,()-> customerService.makePayment(request));
    }

}