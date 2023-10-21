package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.LoanOfficer;
import com.example.KweekCashLoanApp.data.repositories.LoanOfficerRepository;
import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.ICustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.KweekCashLoanApp.data.enums.LoanStatus.APPROVED;
import static com.example.KweekCashLoanApp.data.enums.LoanStatus.REJECTED;
import static com.example.KweekCashLoanApp.services.implementation.TestVariables.*;
import static com.example.KweekCashLoanApp.services.implementation.TestVariables.buildLoanRequestForDavid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoanOfficerServiceTest {
    @Autowired
    private LoanOfficerService loanOfficerService;
    @Autowired
    private LoanOfficerRepository loanOfficerRepository;
    @Autowired
    private ICustomerService customerService;

    @Test
    void objectNotNull(){
        assertThat(loanOfficerService).isNotNull();
    }

    @Test
    void registerLoanOfficerWithCorrectDetails(){
        RegisterUserRequest request = buildFirstLoanOfficer();
        RegisterUserResponse savedOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(savedOfficer);

        assertThat(savedOfficer).isNotNull();
        assertThat(savedOfficer.getFirstName()).isEqualTo("MIKE");
        assertThat(savedOfficer.getMessage()).isNotNull();
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
        RegisterUserRequest request = buildSecondLoanOfficer();
        RegisterUserResponse savedOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(savedOfficer);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("bidemiboyo@gmail.com");
        loginRequest.setPassword("12BigWhiteFav@");
        loginRequest.setAdminLoginCode(savedOfficer.getAdminLoginCode());

        LoginResponse response = loanOfficerService.login(loginRequest);

        assertThat(response).isNotNull();
        assertTrue(response.isLoggedIn());
        assertNotNull(response.getMessage());
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
        RegisterUserRequest request = buildThirdLoanOfficer();
        RegisterUserResponse savedOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(savedOfficer);
        assertEquals("helloboyo@gmail.com", savedOfficer.getEmail());

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("helloboyo@gmail.com");
        updateUserRequest.setNewEmail("michaelboyo@gmail.com");

        UpdateUserResponse response = loanOfficerService.updateLoanOfficerDetails(updateUserRequest);
        assertNotNull(response);
        assertEquals("Update successful",response.getMessage());

        LoanOfficer editedOfficer = loanOfficerRepository.findLoanOfficerByEmail("michaelboyo@gmail.com").get();
        assertEquals(savedOfficer.getLoanOfficerId(), editedOfficer.getLoanOfficerId());
    }
    @Test
    void seeAllPendingLoanRequestsWithCorrectAdminLoginCode(){
        RegisterUserRequest request = buildDavid();
        RegisterUserResponse savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request = buildFifthLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForDavid();
        customerService.applyForALoan(loanApplicationRequest);

        LoanUpdateRequest loanUpdateRequest = new LoanUpdateRequest();
        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());

        List<PendingLoanResponse> pendingLoanResponseList = loanOfficerService.seePendingLoanRequests(loanUpdateRequest);
        assertThat(pendingLoanResponseList).isNotNull();
        assertEquals(1,pendingLoanResponseList.size());
    }
    @Test
    void seeAllPendingLoanRequestsWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87T98e");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seePendingLoanRequests(request));
    }
    @Test
    void seeAllApprovedLoanRequestsWithCorrectAdminLoginCode(){
        RegisterUserRequest request = buildTimi();
        RegisterUserResponse savedCustomer;
        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request =  buildFourthLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForTimi();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);
        Long loanRequestId = loanApplicationResponse.getLoanRequestId();

        LoanUpdateRequest loanUpdateRequest = buildReviewToApproveRequest(loanOfficer, loanRequestId);
        loanOfficerService.reviewLoanRequest(loanUpdateRequest);

        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());

        List<ApprovedLoanResponse> foundResponses = loanOfficerService.seeApprovedLoanRequests(loanUpdateRequest);

        assertThat(foundResponses).isNotNull();
        assertThat(foundResponses).hasSizeBetween(1,4);
    }
    @Test
    void seeAllApprovedLoanRequestsWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("3Pzn87TlaB");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seeApprovedLoanRequests(request));
    }

    @Test
    void seeAllActiveLoansWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("3Pzn87TlaB");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seeActiveLoans(request));
    }
    @Test
    void seeAllRejectedLoanRequestsWithCorrectAdminLoginCode(){
        RegisterUserRequest request = buildSecondUnknown();
        RegisterUserResponse savedCustomer;
        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request =  buildNinthLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForSecondUnknown();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);
        Long loanRequestId = loanApplicationResponse.getLoanRequestId();

        LoanUpdateRequest loanUpdateRequest = buildReviewToRejectRequest(loanOfficer, loanRequestId);
        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());
        loanUpdateRequest.setAuthorizationCode(loanOfficer.getAuthorizationCode());

        String response = loanOfficerService.reviewLoanRequest(loanUpdateRequest);
        assertEquals("Review Successful",response);

        List<RejectedLoanResponse> foundResponses = loanOfficerService.seeRejectedLoanRequests(loanUpdateRequest);
        assertThat(foundResponses).isNotNull();
        assertEquals(1,foundResponses.size());
    }
    @Test
    void seeAllRejectedLoanRequestsWithWrongAdminLoginCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("3Pzn87TlaB");
        assertThrows(ObjectNotFoundException.class, ()-> loanOfficerService.seeRejectedLoanRequests(request));
    }
    @Test
    void reviewAndAcceptLoanRequestWithCorrectLoginDetails(){
        RegisterUserRequest request = buildCephas();
        RegisterUserResponse savedCustomer;
        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request =  buildSixthLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForCephas();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);
        Long loanRequestId = loanApplicationResponse.getLoanRequestId();

        LoanUpdateRequest loanUpdateRequest = buildReviewToApproveRequest(loanOfficer, loanRequestId);
        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());
        loanUpdateRequest.setAuthorizationCode(loanOfficer.getAuthorizationCode());

        String response = loanOfficerService.reviewLoanRequest(loanUpdateRequest);
        assertEquals("Review Successful",response);
    }
    @Test
    void reviewAndAcceptLoanRequestWithWrongAdminCode(){
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlaB");
        request.setAuthorizationCode("6118481467");
        request.setLoanRequestId(102L);
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
        request.setLoanRequestId(102L);
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
        RegisterUserRequest request = buildFirstUnknown();
        RegisterUserResponse savedCustomer;
        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request =  buildSeventhLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForUnknown();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);
        Long loanRequestId = loanApplicationResponse.getLoanRequestId();

        LoanUpdateRequest loanUpdateRequest = buildReviewToRejectRequest(loanOfficer, loanRequestId);
        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());
        loanUpdateRequest.setAuthorizationCode(loanOfficer.getAuthorizationCode());

        String response = loanOfficerService.reviewLoanRequest(loanUpdateRequest);
        assertEquals("Review Successful",response);
    }
    @Test
    void reviewAndRejectLoanRequestWithWrongAdminCode() {
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlpG");
        request.setAuthorizationCode("6968481467");
        request.setLoanRequestId(102L);
        request.setLoanStatus(REJECTED);
        request.setMessage("Bad credit check");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.reviewLoanRequest(request));
    }
    @Test
    void reviewAndRejectLoanRequestWithWrongAuthorizationCode() {
        LoanUpdateRequest request = new LoanUpdateRequest();
        request.setAdminLoginCode("2Nzn87TlpG");
        request.setAuthorizationCode("6968481476");
        request.setLoanRequestId(102L);
        request.setLoanStatus(REJECTED);
        request.setMessage("Bad credit check");

        assertThrows(ObjectNotFoundException.class,()-> loanOfficerService.reviewLoanRequest(request));
    }
    @Test
    void generateLoanAgreementFormWithCorrectUniqueCode(){
        RegisterUserRequest request = buildLegend();
        RegisterUserResponse savedCustomer;
        savedCustomer = customerService.registerCustomer(request);
        assertNotNull(savedCustomer);

        request =  buildEighthLoanOfficer();
        RegisterUserResponse loanOfficer = loanOfficerService.registerLoanOfficer(request);
        assertNotNull(loanOfficer);

        LoanApplicationRequest loanApplicationRequest = buildLoanRequestForLegend();
        LoanApplicationResponse loanApplicationResponse = customerService.applyForALoan(loanApplicationRequest);
        Long loanRequestId = loanApplicationResponse.getLoanRequestId();

        LoanUpdateRequest loanUpdateRequest = buildReviewToApproveRequest(loanOfficer, loanRequestId);
        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());
        loanUpdateRequest.setAuthorizationCode(loanOfficer.getAuthorizationCode());

        String response = loanOfficerService.reviewLoanRequest(loanUpdateRequest);
        assertEquals("Review Successful",response);


        loanApplicationRequest.setUniqueCode(loanApplicationResponse.getUniqueCode());
        LoanAgreementResponse loanAgreementResponse = loanOfficerService.generateLoanAgreementForm(loanApplicationRequest);

        assertThat(loanAgreementResponse).isNotNull();
        assertEquals("Semi-Annually",loanAgreementResponse.getRepaymentPreference());
    }
}