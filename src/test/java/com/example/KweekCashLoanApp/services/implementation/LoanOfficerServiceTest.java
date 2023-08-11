package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.dtos.requests.LoginRequest;
import com.example.KweekCashLoanApp.dtos.requests.RegisterUserRequest;
import com.example.KweekCashLoanApp.dtos.requests.UpdateUserRequest;
import com.example.KweekCashLoanApp.dtos.responses.FindUserResponse;
import com.example.KweekCashLoanApp.dtos.responses.LoginResponse;
import com.example.KweekCashLoanApp.dtos.responses.RegisterUserResponse;
import com.example.KweekCashLoanApp.dtos.responses.UpdateUserResponse;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoanOfficerServiceTest {
    @Autowired
    private LoanOfficerService loanOfficerService;

    @Test
    void objectNotNull(){
        assertThat(loanOfficerService).isNotNull();
    }

    @Test
    void registerLoanOfficerWithCorrectDetails(){
        RegisterUserRequest newOfficer = new RegisterUserRequest();

        newOfficer.setFirstName("Enzo");
        newOfficer.setLastName("Fernandez");
        newOfficer.setEmail("enzomoney@gmail.com");
        newOfficer.setPassword("BigEnzo@98");
        newOfficer.setPhoneNumber("08066778899");
        newOfficer.setStreetNumber("54");
        newOfficer.setStreetName("Marilyn Munroe");
        newOfficer.setTown("Ajah");
        newOfficer.setState("Lagos");

        RegisterUserResponse savedOfficer = new RegisterUserResponse();
        try {
            savedOfficer = loanOfficerService.registerLoanOfficer(newOfficer);
        } catch (IncorrectDetailsException e) {
            System.err.println(e.getMessage());
        }

        assertThat(savedOfficer).isNotNull();
        assertThat(savedOfficer.getFirstName()).isEqualTo("Enzo");
        assertThat(savedOfficer.getMessage()).isNotNull();
        System.out.println(savedOfficer);
    }

    @Test
    void registerLoanOfficerWithIncorrectEmailFormat() {
        RegisterUserRequest newOfficer = new RegisterUserRequest();

        newOfficer.setFirstName("Mykailo");
        newOfficer.setLastName("Mudryk");
        newOfficer.setEmail("mmudryk.yahoo.co.uk");
        newOfficer.setPassword("UkraineMM@10");
        newOfficer.setPhoneNumber("08066778899");
        newOfficer.setStreetNumber("54");
        newOfficer.setStreetName("Marilyn Munroe");
        newOfficer.setTown("Ajah");
        newOfficer.setState("Lagos");

        assertThrows(IncorrectDetailsException.class,()-> loanOfficerService.registerLoanOfficer(newOfficer));
    }

    @Test
    void registerLoanOfficerWithWrongPasswordFormat() {
        RegisterUserRequest newOfficer = new RegisterUserRequest();

        newOfficer.setFirstName("Mykailo");
        newOfficer.setLastName("Mudryk");
        newOfficer.setEmail("mmudryk@yahoo.co.uk");
        newOfficer.setPassword("UkraineMM");
        newOfficer.setPhoneNumber("08066778899");
        newOfficer.setStreetNumber("54");
        newOfficer.setStreetName("Marilyn Munroe");
        newOfficer.setTown("Ajah");
        newOfficer.setState("Lagos");

        assertThrows(IncorrectDetailsException.class,()-> loanOfficerService.registerLoanOfficer(newOfficer));
    }

    @Test
    void testLoginWithCorrectLoginDetails(){
        LoginRequest request = new LoginRequest();
        request.setPassword("BigEnzo@98");
        request.setEmail("enzomoney@gmail.com");
        request.setAdminLoginCode("6XIdAcI92M");

        LoginResponse response = new LoginResponse();
        try {
            response =  loanOfficerService.login(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertThat(response).isNotNull();
        assertTrue(response.isLoggedIn());
    }
    @Test
    void testLoginWithIncorrectEmail() {
        LoginRequest request = new LoginRequest();
        request.setPassword("BigEnzo@98");
        request.setEmail("enzo@gmail.com");
        request.setAdminLoginCode("6XIdAcI92M");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.login(request));
    }
    @Test
    void testLoginWithIncorrectPassword() {
        LoginRequest request = new LoginRequest();
        request.setPassword("BigEnzo@99");
        request.setEmail("enzomoney@gmail.com");
        request.setAdminLoginCode("6XIdAcI92M");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.login(request));
    }
    @Test
    void testLoginWithIncorrectAdminLoginCode() {
        LoginRequest request = new LoginRequest();
        request.setPassword("BigEnzo@98");
        request.setEmail("enzomoney@gmail.com");
        request.setAdminLoginCode("6XIdAcI9");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.login(request));
    }

    @Test
    void updateLoanOfficerDetails(){
        FindUserResponse response = new FindUserResponse();
        try {
            response = loanOfficerService.("jackson@gmail.com");
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(response);
        assertEquals("Benjamin",response.getFirstName());

        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("ename@gmail.com");
        request.setFirstName("Inyang");

        UpdateUserResponse response = new UpdateUserResponse();
        try {
            response = loanOfficerService.updateLoanOfficerDetails(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertNotNull(response);
        assertThat(response.getFirstName()).isNotEqualToIgnoringCase("Enzo");
        assertEquals("Pablo",response.getFirstName());
    }
}