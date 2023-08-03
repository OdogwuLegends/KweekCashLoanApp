package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.dtos.requests.LoginRequest;
import com.example.KweekCashLoanApp.dtos.requests.RegisterUserRequest;
import com.example.KweekCashLoanApp.dtos.responses.LoginResponse;
import com.example.KweekCashLoanApp.dtos.responses.RegisterUserResponse;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


}