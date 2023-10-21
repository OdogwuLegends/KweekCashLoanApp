package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.ActiveLoans;
import com.example.KweekCashLoanApp.dtos.responses.ActiveLoanResponse;
import com.example.KweekCashLoanApp.dtos.responses.ApprovedLoanResponse;
import com.example.KweekCashLoanApp.services.interfaces.IActiveLoansService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.example.KweekCashLoanApp.data.enums.LoanStatus.APPROVED;
import static com.example.KweekCashLoanApp.utils.AppUtils.amountToPay;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActiveLoansServiceTest {
    @Autowired
    private IActiveLoansService activeLoansService;

    @Test
    void saveActiveLoans() {
        ApprovedLoanResponse approvedLoanResponse = buildFirstApprovedLoan();
        ActiveLoanResponse response = activeLoansService.saveActiveLoans(approvedLoanResponse);
        assertNotNull(response);
    }

    @Test
    void findByCustomerId() {
        ApprovedLoanResponse approvedLoanResponse = buildSecondApprovedLoan();
        ActiveLoanResponse response = activeLoansService.saveActiveLoans(approvedLoanResponse);
        assertNotNull(response);

        ActiveLoanResponse foundCustomer = activeLoansService.findByCustomerId(response.getCustomerId());
        assertNotNull(foundCustomer);
    }

    @Test
    void findAllActiveLoans() {
        ApprovedLoanResponse approvedLoanResponse = buildThirdApprovedLoan();
        ActiveLoanResponse response = activeLoansService.saveActiveLoans(approvedLoanResponse);
        assertNotNull(response);

        List<ActiveLoans> activeLoansList = activeLoansService.findAllActiveLoans();
        assertThat(activeLoansList).hasSizeBetween(1,4);
    }

    @Test
    void setNewBalance() {
        ApprovedLoanResponse approvedLoanResponse = buildFourthApprovedLoan();
        ActiveLoanResponse response = activeLoansService.saveActiveLoans(approvedLoanResponse);
        assertNotNull(response);

        String newBalance = activeLoansService.setNewBalance(4L,BigDecimal.valueOf(20000));
        assertNotNull(newBalance);
        assertEquals(newBalance,"Payment of N"+20000+" was successful"+
                "\nYour current loan balance is N180000.00");
    }

    @Test
    void getLoanDetails() {
        ApprovedLoanResponse approvedLoanResponse = buildFifthApprovedLoan();
        ActiveLoanResponse response = activeLoansService.saveActiveLoans(approvedLoanResponse);
        assertNotNull(response);

        ActiveLoanResponse activeLoanResponse = activeLoansService.getLoanDetails(5L);
        assertNotNull(activeLoanResponse);
        assertEquals("CASH PAYMENT", activeLoanResponse.getPaymentMethod());
    }

    private static ApprovedLoanResponse buildFirstApprovedLoan() {
        ApprovedLoanResponse approvedLoanResponse = new ApprovedLoanResponse();

        approvedLoanResponse.setApprovedRequestId(1L);
        approvedLoanResponse.setLoanRequestId(1L);
        approvedLoanResponse.setCustomerId(1L);
        approvedLoanResponse.setUniqueCode("bW234NpQ");
        approvedLoanResponse.setLoanAmount(BigDecimal.valueOf(200000));
        approvedLoanResponse.setLoanTenure("36");
        approvedLoanResponse.setLoanStatus(APPROVED.name());
        approvedLoanResponse.setPurposeOfLoan("Business");
        approvedLoanResponse.setRepaymentPreference("ANNUALLY");
        approvedLoanResponse.setPaymentMethod("CASH PAYMENT");
        approvedLoanResponse.setInterestRate(5.0);
        approvedLoanResponse.setStartDate(LocalDate.of(2023,7,23));
        approvedLoanResponse.setEndDate(LocalDate.of(2026,6,20));
        approvedLoanResponse.setAmountPerPaymentPeriod(amountToPay(BigDecimal.valueOf(200000),"36",5.0));
        approvedLoanResponse.setDateOfApplication(LocalDate.of(2023,6,18));
        approvedLoanResponse.setDateApproved(LocalDate.now());
        return approvedLoanResponse;
    }
    private static ApprovedLoanResponse buildSecondApprovedLoan() {
        ApprovedLoanResponse approvedLoanResponse = new ApprovedLoanResponse();

        approvedLoanResponse.setApprovedRequestId(2L);
        approvedLoanResponse.setLoanRequestId(2L);
        approvedLoanResponse.setCustomerId(2L);
        approvedLoanResponse.setUniqueCode("bW234NpQ");
        approvedLoanResponse.setLoanAmount(BigDecimal.valueOf(200000));
        approvedLoanResponse.setLoanTenure("36");
        approvedLoanResponse.setLoanStatus(APPROVED.name());
        approvedLoanResponse.setPurposeOfLoan("Business");
        approvedLoanResponse.setRepaymentPreference("ANNUALLY");
        approvedLoanResponse.setPaymentMethod("CASH PAYMENT");
        approvedLoanResponse.setInterestRate(5.0);
        approvedLoanResponse.setStartDate(LocalDate.of(2023,7,23));
        approvedLoanResponse.setEndDate(LocalDate.of(2026,6,20));
        approvedLoanResponse.setAmountPerPaymentPeriod(amountToPay(BigDecimal.valueOf(200000),"36",5.0));
        approvedLoanResponse.setDateOfApplication(LocalDate.of(2023,6,18));
        approvedLoanResponse.setDateApproved(LocalDate.now());
        return approvedLoanResponse;
    }
    private static ApprovedLoanResponse buildThirdApprovedLoan() {
        ApprovedLoanResponse approvedLoanResponse = new ApprovedLoanResponse();

        approvedLoanResponse.setApprovedRequestId(3L);
        approvedLoanResponse.setLoanRequestId(3L);
        approvedLoanResponse.setCustomerId(3L);
        approvedLoanResponse.setUniqueCode("bW234NpQ");
        approvedLoanResponse.setLoanAmount(BigDecimal.valueOf(200000));
        approvedLoanResponse.setLoanTenure("36");
        approvedLoanResponse.setLoanStatus(APPROVED.name());
        approvedLoanResponse.setPurposeOfLoan("Business");
        approvedLoanResponse.setRepaymentPreference("ANNUALLY");
        approvedLoanResponse.setPaymentMethod("CASH PAYMENT");
        approvedLoanResponse.setInterestRate(5.0);
        approvedLoanResponse.setStartDate(LocalDate.of(2023,7,23));
        approvedLoanResponse.setEndDate(LocalDate.of(2026,6,20));
        approvedLoanResponse.setAmountPerPaymentPeriod(amountToPay(BigDecimal.valueOf(200000),"36",5.0));
        approvedLoanResponse.setDateOfApplication(LocalDate.of(2023,6,18));
        approvedLoanResponse.setDateApproved(LocalDate.now());
        return approvedLoanResponse;
    }
    private static ApprovedLoanResponse buildFourthApprovedLoan() {
        ApprovedLoanResponse approvedLoanResponse = new ApprovedLoanResponse();

        approvedLoanResponse.setApprovedRequestId(4L);
        approvedLoanResponse.setLoanRequestId(4L);
        approvedLoanResponse.setCustomerId(4L);
        approvedLoanResponse.setUniqueCode("bW234NpQ");
        approvedLoanResponse.setLoanAmount(BigDecimal.valueOf(200000));
        approvedLoanResponse.setLoanTenure("36");
        approvedLoanResponse.setLoanStatus(APPROVED.name());
        approvedLoanResponse.setPurposeOfLoan("Business");
        approvedLoanResponse.setRepaymentPreference("ANNUALLY");
        approvedLoanResponse.setPaymentMethod("CASH PAYMENT");
        approvedLoanResponse.setInterestRate(5.0);
        approvedLoanResponse.setStartDate(LocalDate.of(2023,7,23));
        approvedLoanResponse.setEndDate(LocalDate.of(2026,6,20));
        approvedLoanResponse.setAmountPerPaymentPeriod(amountToPay(BigDecimal.valueOf(200000),"36",5.0));
        approvedLoanResponse.setDateOfApplication(LocalDate.of(2023,6,18));
        approvedLoanResponse.setDateApproved(LocalDate.now());
        return approvedLoanResponse;
    }
    private static ApprovedLoanResponse buildFifthApprovedLoan() {
        ApprovedLoanResponse approvedLoanResponse = new ApprovedLoanResponse();

        approvedLoanResponse.setApprovedRequestId(5L);
        approvedLoanResponse.setLoanRequestId(5L);
        approvedLoanResponse.setCustomerId(5L);
        approvedLoanResponse.setUniqueCode("bW234NpQ");
        approvedLoanResponse.setLoanAmount(BigDecimal.valueOf(200000));
        approvedLoanResponse.setLoanTenure("36");
        approvedLoanResponse.setLoanStatus(APPROVED.name());
        approvedLoanResponse.setPurposeOfLoan("Business");
        approvedLoanResponse.setRepaymentPreference("ANNUALLY");
        approvedLoanResponse.setPaymentMethod("CASH PAYMENT");
        approvedLoanResponse.setInterestRate(5.0);
        approvedLoanResponse.setStartDate(LocalDate.of(2023,7,23));
        approvedLoanResponse.setEndDate(LocalDate.of(2026,6,20));
        approvedLoanResponse.setAmountPerPaymentPeriod(amountToPay(BigDecimal.valueOf(200000),"36",5.0));
        approvedLoanResponse.setDateOfApplication(LocalDate.of(2023,6,18));
        approvedLoanResponse.setDateApproved(LocalDate.now());
        return approvedLoanResponse;
    }
}