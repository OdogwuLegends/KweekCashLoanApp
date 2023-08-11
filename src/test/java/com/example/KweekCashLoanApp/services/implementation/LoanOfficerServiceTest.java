package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import com.example.KweekCashLoanApp.data.models.LoanOfficer;
import com.example.KweekCashLoanApp.data.repositories.LoanOfficerRepository;
import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.KweekCashLoanApp.data.enums.LoanStatus.APPROVED;
import static com.example.KweekCashLoanApp.data.enums.LoanStatus.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoanOfficerServiceTest {
    @Autowired
    private LoanOfficerService loanOfficerService;
    @Autowired
    private LoanOfficerRepository loanOfficerRepository;

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
        LoanOfficer foundOfficer =  loanOfficerRepository.findLoanOfficerByEmail("ename@gmail.com");

        assertNotNull(foundOfficer);
        assertEquals("Inemesit",foundOfficer.getFirstName());

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
        assertThat(response.getFirstName()).isNotEqualToIgnoringCase("Inemesit");
        assertEquals("Inyang",response.getFirstName());
        assertEquals("ename@gmail.com",response.getEmail());
    }
    @Test
    void seeAllPendingLoanRequestsWithCorrectAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");

        List<PendingLoanResponse> foundResponses = new ArrayList<>();
        try {
            foundResponses = loanOfficerService.seePendingLoanRequests(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertThat(foundResponses).isNotNull();
        assertEquals(2,foundResponses.size());
    }
    @Test
    void seeAllPendingLoanRequestsWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87T98e");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seePendingLoanRequests(request));
    }
    @Test
    void seeAllApprovedLoanRequestsWithCorrectAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");

        List<ApprovedLoanResponse> foundResponses = new ArrayList<>();
        try {
            foundResponses = loanOfficerService.seeApprovedLoanRequests(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertThat(foundResponses).isNotNull();
        assertEquals(0,foundResponses.size());
    }
    @Test
    void seeAllApprovedLoanRequestsWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("3Pzn87TlaB");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seeApprovedLoanRequests(request));
    }
    @Test
    void seeAllActiveLoansWithCorrectAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");

        List<ActiveLoanResponse> foundResponses = new ArrayList<>();
        try {
            foundResponses = loanOfficerService.seeActiveLoans(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertThat(foundResponses).isNotNull();
        assertEquals(0,foundResponses.size());
    }
    @Test
    void seeAllActiveLoansWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("3Pzn87TlaB");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seeActiveLoans(request));
    }
    @Test
    void seeAllRejectedLoanRequestsWithCorrectAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");

        List<RejectedLoanResponse> foundResponses = new ArrayList<>();
        try {
            foundResponses = loanOfficerService.seeRejectedLoanRequests(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertThat(foundResponses).isNotNull();
        assertEquals(0,foundResponses.size());
    }
    @Test
    void seeAllRejectedLoanRequestsWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("3Pzn87TlaB");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seeRejectedLoanRequests(request));
    }
    @Test
    void reviewAndAcceptLoanRequestWithCorrectLoginDetails(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");
        request.setAuthorizationCode("6968481467");
        request.setLoanRequestId(52);
        request.setLoanStatus(APPROVED);
        request.setInterestRate(10.0);
        request.setStartDate("15/08/2023");
        request.setEndDate("30/06/2027");
        request.setDateApproved("12/08/2023");
        request.setMessage("");

        String response = null;
        try {
           response = loanOfficerService.reviewLoanRequest(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertEquals("Review Successful",response);
    }
    @Test
    void reviewAndAcceptLoanRequestWithWrongAdminCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");
        request.setAuthorizationCode("6118481467");
        request.setLoanRequestId(102);
        request.setLoanStatus(APPROVED);
        request.setInterestRate(10.0);
        request.setStartDate("15/08/2023");
        request.setEndDate("30/06/2027");
        request.setDateApproved("12/08/2023");
        request.setMessage("");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.reviewLoanRequest(request));
    }
    @Test
    void reviewAndAcceptLoanRequestWithWrongAuthorizationCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87T9H4");
        request.setAuthorizationCode("6968481467");
        request.setLoanRequestId(102);
        request.setLoanStatus(APPROVED);
        request.setInterestRate(10.0);
        request.setStartDate("15/08/2023");
        request.setEndDate("30/06/2027");
        request.setDateApproved("12/08/2023");
        request.setMessage("");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.reviewLoanRequest(request));
    }
    @Test
    void reviewAndRejectLoanRequestWithCorrectLoginDetails(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");
        request.setAuthorizationCode("6968481467");
        request.setLoanRequestId(102);
        request.setLoanStatus(REJECTED );
        request.setMessage("Bad credit check");

        String response = null;
        try {
            response = loanOfficerService.reviewLoanRequest(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertEquals("Review Successful",response);
    }
    @Test
    void reviewAndRejectLoanRequestWithWrongAdminCode() {
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlpG");
        request.setAuthorizationCode("6968481467");
        request.setLoanRequestId(102);
        request.setLoanStatus(REJECTED);
        request.setMessage("Bad credit check");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.reviewLoanRequest(request));
    }
    @Test
    void reviewAndRejectLoanRequestWithWrongAuthorizationCode() {
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlpG");
        request.setAuthorizationCode("6968481476");
        request.setLoanRequestId(102);
        request.setLoanStatus(REJECTED);
        request.setMessage("Bad credit check");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.reviewLoanRequest(request));
    }
    @Test
    void generateLoanAgreementFormWithCorrectUniqueCode(){
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setUniqueCode("1hRjuDp2lM");

        LoanAgreementResponse response = new LoanAgreementResponse();
        try {
            response = loanOfficerService.generateLoanAgreementForm(request);
        } catch (ObjectNotFoundException e) {
            System.err.println(e.getMessage());
        }
        assertThat(response).isNotNull();
        assertEquals("Semi-Annually",response.getRepaymentPreference());
    }
}