package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.example.KweekCashLoanApp.data.enums.LoanTenure.SIXTY_MONTHS;
import static com.example.KweekCashLoanApp.data.enums.PaymentMethod.SALARY_DEDUCTIONS;
import static com.example.KweekCashLoanApp.data.enums.RepaymentPreference.SEMI_ANNUALLY;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void testCustomerServiceNotNull(){
        assertNotNull(customerService);
    }
    @Test
    void testToRegisterCustomerWithCorrectDetails(){
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Favour");
        request.setLastName("Black");
        request.setEmail("fav93@gmail.com");
        request.setPassword("12BigBlackFav");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");

        RegisterUserResponse savedCustomer = new RegisterUserResponse();
        try {
            savedCustomer = customerService.registerCustomer(request);
        } catch (IncorrectDetailsException e) {
            System.err.println(e.getMessage());;
        }
        assertEquals("Welcome "+savedCustomer.getFirstName() + " "+savedCustomer.getLastName()+". Your account creation was successful.",savedCustomer.toString());
    }
    @Test
    void testToRegisterCustomerWithIncorrectEmail(){
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
    void testToRegisterCustomerWithWrongPasswordFormat(){
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
        LoginRequest request = new LoginRequest();
        request.setEmail("legend@gmail.com");
        request.setPassword("MyLeggy@1#");

        LoginResponse response = new LoginResponse();

        try {
            response = customerService.logIn(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(response);
        assertTrue(response.isLoggedIn());
        assertEquals("Log in successful",response.getMessage());
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
        FindUserResponse response = new FindUserResponse();
        try {
            response = customerService.findCustomerById(1);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(response);
        assertEquals("Legend",response.getLastName());
    }
    @Test
    void testFindCustomerWithWrongId(){
        assertThrows(ObjectNotFoundException.class, ()-> customerService.findCustomerById(150));
    }
    @Test
    void testFindCustomerWithCorrectEmail(){
        FindUserResponse response = new FindUserResponse();
        try {
            response = customerService.findCustomerByEmail("legend@gmail.com");
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(response);
        assertEquals("Caterer",response.getOccupation());
    }
    @Test
    void testFindCustomerWithWrongEmail(){
        assertThrows(ObjectNotFoundException.class,()-> customerService.findCustomerByEmail("cephas@gmail.com"));
    }
    @Test
    void testUpdateCustomerDetailsWithCorrectLoginEmail(){
        FindUserResponse response = new FindUserResponse();
        try {
            response = customerService.findCustomerByEmail("jackson@gmail.com");
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(response);
        assertEquals("Benjamin",response.getFirstName());

        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("jackson@gmail.com");
        request.setFirstName("Billie");

        UpdateUserResponse updatedResponse = new UpdateUserResponse();
        try {
            updatedResponse = customerService.updateCustomerDetails(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(updatedResponse);
        assertEquals("Billie",updatedResponse.getFirstName());
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
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setEmail("legend@gmail.com");
        request.setLoanTenure(SIXTY_MONTHS);
        request.setLoanAmount(BigDecimal.valueOf(500000));
        request.setPurposeOfLoan("Agriculture");
        request.setRepaymentPreference(SEMI_ANNUALLY);
        request.setPaymentMethod(SALARY_DEDUCTIONS);

        LoanApplicationResponse response = customerService.applyForALoan(request);
        assertNotNull(response);
        assertEquals("John",response.getFirstName());
        assertEquals(BigDecimal.valueOf(500000),response.getLoanAmount());
    }
    @Test
    void testCheckLoanApplicationStatus(){
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setUniqueCode("1hRjuDp2lM");

        LoanApplicationResponse response = new LoanApplicationResponse();
        try {
            response = customerService.checkApplicationStatus(request);
        } catch (IncorrectDetailsException e) {
            System.err.println(e.getMessage());
        }
        assertEquals("Awaiting approval. Please check back in 48 hours.",response.getMessage());
    }
    @Test
    void testCheckBalanceOnActiveLoan(){
        PaymentRequest request = new PaymentRequest();
        request.setEmail("jackson@gmail.com");

        String balance = null;
        try {
            balance = customerService.checkLoanBalance(request);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals("\t\t\t\t\t\t\t\t\tLOAN DETAILS\n" +
                "\n" +
                "LOAN AMOUNT -> 600000.00\n" +
                "TOTAL AMOUNT REPAID -> 0.00\n" +
                "BALANCE -> 600000.00\n" +
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
        PaymentRequest request = new PaymentRequest();
        request.setEmail("jackson@gmail.com");
        request.setAmount(BigDecimal.valueOf(20000));

        String loanBalance = null;
        try {
            loanBalance = customerService.makePayment(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }

        assertEquals( "Payment of N"+20000+" was successful"+
                "\nYour current loan balance is N500000.00",loanBalance);
    }
    @Test
    void testMakePaymentWithoutActiveLoan(){
        PaymentRequest request = new PaymentRequest();
        request.setEmail("legend@gmail.com");
        request.setAmount(BigDecimal.valueOf(20000));

        assertThrows(ObjectNotFoundException.class,()-> customerService.makePayment(request));
    }

}