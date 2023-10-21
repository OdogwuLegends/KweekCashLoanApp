package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import com.example.KweekCashLoanApp.data.models.Customer;
import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.requests.LoanUpdateRequest;
import com.example.KweekCashLoanApp.dtos.responses.LoanApplicationResponse;
import com.example.KweekCashLoanApp.dtos.responses.PendingLoanResponse;
import com.example.KweekCashLoanApp.services.interfaces.IPendingLoansService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static com.example.KweekCashLoanApp.data.enums.LoanStatus.APPROVED;
import static com.example.KweekCashLoanApp.data.enums.LoanTenure.TWENTY_FOUR_MONTHS;
import static com.example.KweekCashLoanApp.data.enums.PaymentMethod.STANDING_INSTRUCTION;
import static com.example.KweekCashLoanApp.data.enums.RepaymentPreference.QUARTERLY;
import static com.example.KweekCashLoanApp.utils.HardcodedValues.REQUEST_AWAITING_APPROVAL;
import static com.example.KweekCashLoanApp.utils.HardcodedValues.REVIEW_SUCCESSFUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class PendingLoansServiceTest {
    @Autowired
    private IPendingLoansService pendingLoansService;

    @Test
    void requestForALoan() {
        LoanApplicationRequest loanApplicationRequest = buildFirstLoanRequest();

        Customer customer = firstCustomer();

        LoanApplicationResponse applicationResponse = pendingLoansService.requestForALoan(loanApplicationRequest,customer);
        assertNotNull(applicationResponse);
    }

    @Test
    void confirmStatus() {
        LoanApplicationRequest loanApplicationRequest = buildSecondLoanRequest();
        Customer customer = secondCustomer();
        LoanApplicationResponse applicationResponse = pendingLoansService.requestForALoan(loanApplicationRequest,customer);
        assertNotNull(applicationResponse);

        loanApplicationRequest.setUniqueCode(applicationResponse.getUniqueCode());
        LoanApplicationResponse response = pendingLoansService.confirmStatus(loanApplicationRequest);
        assertEquals(response.getMessage(),REQUEST_AWAITING_APPROVAL);
    }

    @Test
    void findRequestById() {
        LoanApplicationRequest loanApplicationRequest = buildThirdLoanRequest();
        Customer customer = thirdCustomer();
        LoanApplicationResponse applicationResponse = pendingLoansService.requestForALoan(loanApplicationRequest,customer);
        assertNotNull(applicationResponse);

        Long id = applicationResponse.getLoanRequestId();
        PendingLoanResponse pendingLoanRequest = pendingLoansService.findRequestById(id);
        assertNotNull(pendingLoanRequest);
        assertEquals("BUSINESS",pendingLoanRequest.getPurposeOfLoan());
    }

    @Test
    void findAllPendingRequests() {
        List<PendingLoanRequests> pendingLoanRequestsList = pendingLoansService.findAllPendingRequests();
        assertThat(pendingLoanRequestsList).hasSizeBetween(0,4);
    }

    @Test
    void updateRequestDetails() {
        LoanApplicationRequest loanApplicationRequest = buildFourthLoanRequest();
        Customer customer = fourthCustomer();
        LoanApplicationResponse applicationResponse = pendingLoansService.requestForALoan(loanApplicationRequest,customer);
        assertNotNull(applicationResponse);

        LoanUpdateRequest loanUpdateRequest = new LoanUpdateRequest();
        loanUpdateRequest.setLoanRequestId(applicationResponse.getLoanRequestId());
        loanUpdateRequest.setLoanStatus(APPROVED);
        loanUpdateRequest.setInterestRate(5.0);
        loanUpdateRequest.setStartDate("15/07/2023");
        loanUpdateRequest.setEndDate("12/06/2027");
        loanUpdateRequest.setDateApproved("13/07/2023");
//        loanUpdateRequest.setAuthorizationCode();
//        loanUpdateRequest.setAdminLoginCode();

        String response = pendingLoansService.updateRequestDetails(loanUpdateRequest);
        assertNotNull(response);
        assertEquals(REVIEW_SUCCESSFUL, response);

    }

    private static LoanApplicationRequest buildFirstLoanRequest() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(200000));
        loanApplicationRequest.setLoanTenure(TWENTY_FOUR_MONTHS);
        loanApplicationRequest.setPurposeOfLoan("BUSINESS");
        loanApplicationRequest.setEmail("askofmark@gmail.com");
        loanApplicationRequest.setPaymentMethod(STANDING_INSTRUCTION);
        loanApplicationRequest.setRepaymentPreference(QUARTERLY);
        return loanApplicationRequest;
    }
    private static LoanApplicationRequest buildSecondLoanRequest() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(200000));
        loanApplicationRequest.setLoanTenure(TWENTY_FOUR_MONTHS);
        loanApplicationRequest.setPurposeOfLoan("BUSINESS");
        loanApplicationRequest.setEmail("legjnr@gmail.com");
        loanApplicationRequest.setPaymentMethod(STANDING_INSTRUCTION);
        loanApplicationRequest.setRepaymentPreference(QUARTERLY);
        return loanApplicationRequest;
    }
    private static LoanApplicationRequest buildThirdLoanRequest() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(200000));
        loanApplicationRequest.setLoanTenure(TWENTY_FOUR_MONTHS);
        loanApplicationRequest.setPurposeOfLoan("BUSINESS");
        loanApplicationRequest.setEmail("coutinho@gmail.com");
        loanApplicationRequest.setPaymentMethod(STANDING_INSTRUCTION);
        loanApplicationRequest.setRepaymentPreference(QUARTERLY);
        return loanApplicationRequest;
    }
    private static LoanApplicationRequest buildFourthLoanRequest() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(200000));
        loanApplicationRequest.setLoanTenure(TWENTY_FOUR_MONTHS);
        loanApplicationRequest.setPurposeOfLoan("BUSINESS");
        loanApplicationRequest.setEmail("ename@gmail.com");
        loanApplicationRequest.setPaymentMethod(STANDING_INSTRUCTION);
        loanApplicationRequest.setRepaymentPreference(QUARTERLY);
        return loanApplicationRequest;
    }
    private static Customer firstCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setEmail("askofmark@gmail.com");
        return customer;
    }
    private static Customer secondCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(2L);
        customer.setEmail("legjnr@gmail.com");
        return customer;
    }
    private static Customer thirdCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(3L);
        customer.setEmail("coutinho@gmail.com");
        return customer;
    }
    private static Customer fourthCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(4L);
        customer.setEmail("ename@gmail.com");
        return customer;
    }
}