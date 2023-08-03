package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.dtos.requests.RegisterUserRequest;
import com.example.KweekCashLoanApp.dtos.responses.RegisterUserResponse;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
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


}