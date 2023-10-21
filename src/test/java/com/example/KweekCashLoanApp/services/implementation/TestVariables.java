package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.requests.LoanUpdateRequest;
import com.example.KweekCashLoanApp.dtos.requests.RegisterUserRequest;
import com.example.KweekCashLoanApp.dtos.responses.RegisterUserResponse;

import java.math.BigDecimal;

import static com.example.KweekCashLoanApp.data.enums.LoanStatus.APPROVED;
import static com.example.KweekCashLoanApp.data.enums.LoanStatus.REJECTED;
import static com.example.KweekCashLoanApp.data.enums.LoanTenure.SIXTY_MONTHS;
import static com.example.KweekCashLoanApp.data.enums.PaymentMethod.SALARY_DEDUCTIONS;
import static com.example.KweekCashLoanApp.data.enums.RepaymentPreference.SEMI_ANNUALLY;

public class TestVariables {

    public static RegisterUserRequest buildFavourBlack() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Favour");
        request.setLastName("Black");
        request.setEmail("fav93@gmail.com");
        request.setPassword("12BigBlackFav#");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildFavourWhite() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Favour");
        request.setLastName("White");
        request.setEmail("favsam@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildLegend() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Legend");
        request.setLastName("Odogwu");
        request.setEmail("legjnr@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildDominic() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Dominic");
        request.setLastName("Szoboszlai");
        request.setEmail("domdom@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildInem() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Inem");
        request.setLastName("Udo");
        request.setEmail("ename@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildMark() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Mark");
        request.setLastName("Memga");
        request.setEmail("askofmark@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildCephas() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Cephas");
        request.setLastName("Hemba");
        request.setEmail("cephas@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildDavid() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("David");
        request.setLastName("Sylva");
        request.setEmail("david@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildTimi() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Timi");
        request.setLastName("Leyin");
        request.setEmail("timi@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildFirstUnknown() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Unknown");
        request.setLastName("Unknown");
        request.setEmail("unknown@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static RegisterUserRequest buildSecondUnknown() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setFirstName("Unknown");
        request.setLastName("Unknown");
        request.setEmail("secondunknown@gmail.com");
        request.setPassword("12BigWhiteFav@");
        request.setOccupation("Banking");
        request.setPhoneNumber("08045983697");
        request.setStreetNumber("34");
        request.setStreetName("Curtis Jones Street");
        request.setTown("Ikeja");
        request.setState("Lagos");
        return request;
    }
    public static LoanApplicationRequest buildLoanRequestForMark() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setEmail("askofmark@gmail.com");
        loanApplicationRequest.setLoanTenure(SIXTY_MONTHS);
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(500000));
        loanApplicationRequest.setPurposeOfLoan("Agriculture");
        loanApplicationRequest.setRepaymentPreference(SEMI_ANNUALLY);
        loanApplicationRequest.setPaymentMethod(SALARY_DEDUCTIONS);
        return loanApplicationRequest;
    }
    public static LoanApplicationRequest buildLoanRequestForLegend() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setEmail("legjnr@gmail.com");
        loanApplicationRequest.setLoanTenure(SIXTY_MONTHS);
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(500000));
        loanApplicationRequest.setPurposeOfLoan("Agriculture");
        loanApplicationRequest.setRepaymentPreference(SEMI_ANNUALLY);
        loanApplicationRequest.setPaymentMethod(SALARY_DEDUCTIONS);
        return loanApplicationRequest;
    }
    public static LoanApplicationRequest buildLoanRequestForCephas() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setEmail("cephas@gmail.com");
        loanApplicationRequest.setLoanTenure(SIXTY_MONTHS);
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(500000));
        loanApplicationRequest.setPurposeOfLoan("Agriculture");
        loanApplicationRequest.setRepaymentPreference(SEMI_ANNUALLY);
        loanApplicationRequest.setPaymentMethod(SALARY_DEDUCTIONS);
        return loanApplicationRequest;
    }
    public static LoanApplicationRequest buildLoanRequestForDavid() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setEmail("david@gmail.com");
        loanApplicationRequest.setLoanTenure(SIXTY_MONTHS);
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(500000));
        loanApplicationRequest.setPurposeOfLoan("Agriculture");
        loanApplicationRequest.setRepaymentPreference(SEMI_ANNUALLY);
        loanApplicationRequest.setPaymentMethod(SALARY_DEDUCTIONS);
        return loanApplicationRequest;
    }
    public static LoanApplicationRequest buildLoanRequestForTimi() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setEmail("timi@gmail.com");
        loanApplicationRequest.setLoanTenure(SIXTY_MONTHS);
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(500000));
        loanApplicationRequest.setPurposeOfLoan("Agriculture");
        loanApplicationRequest.setRepaymentPreference(SEMI_ANNUALLY);
        loanApplicationRequest.setPaymentMethod(SALARY_DEDUCTIONS);
        return loanApplicationRequest;
    }
    public static LoanApplicationRequest buildLoanRequestForUnknown() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setEmail("unknown@gmail.com");
        loanApplicationRequest.setLoanTenure(SIXTY_MONTHS);
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(500000));
        loanApplicationRequest.setPurposeOfLoan("Agriculture");
        loanApplicationRequest.setRepaymentPreference(SEMI_ANNUALLY);
        loanApplicationRequest.setPaymentMethod(SALARY_DEDUCTIONS);
        return loanApplicationRequest;
    }
    public static LoanApplicationRequest buildLoanRequestForSecondUnknown() {
        LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest();
        loanApplicationRequest.setEmail("secondunknown@gmail.com");
        loanApplicationRequest.setLoanTenure(SIXTY_MONTHS);
        loanApplicationRequest.setLoanAmount(BigDecimal.valueOf(500000));
        loanApplicationRequest.setPurposeOfLoan("Agriculture");
        loanApplicationRequest.setRepaymentPreference(SEMI_ANNUALLY);
        loanApplicationRequest.setPaymentMethod(SALARY_DEDUCTIONS);
        return loanApplicationRequest;
    }

    public static LoanUpdateRequest buildReviewToApproveRequest(RegisterUserResponse loanOfficer, Long loanRequestId) {
        LoanUpdateRequest loanUpdateRequest = new LoanUpdateRequest();
        loanUpdateRequest.setLoanRequestId(loanRequestId);
        loanUpdateRequest.setLoanStatus(APPROVED);
        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());
        loanUpdateRequest.setAuthorizationCode(loanOfficer.getAuthorizationCode());
        loanUpdateRequest.setStartDate("15/07/2023");
        loanUpdateRequest.setEndDate("30/06/2027");
        loanUpdateRequest.setDateApproved("13/07/2023");
        loanUpdateRequest.setInterestRate(5.0);
        return loanUpdateRequest;
    }
    public static LoanUpdateRequest buildReviewToRejectRequest(RegisterUserResponse loanOfficer, Long loanRequestId) {
        LoanUpdateRequest loanUpdateRequest = new LoanUpdateRequest();
        loanUpdateRequest.setLoanRequestId(loanRequestId);
        loanUpdateRequest.setLoanStatus(REJECTED);
        loanUpdateRequest.setAdminLoginCode(loanOfficer.getAdminLoginCode());
        loanUpdateRequest.setAuthorizationCode(loanOfficer.getAuthorizationCode());
        loanUpdateRequest.setStartDate("15/07/2023");
        loanUpdateRequest.setEndDate("30/06/2027");
        loanUpdateRequest.setDateApproved("13/07/2023");
        loanUpdateRequest.setInterestRate(5.0);
        loanUpdateRequest.setMessage("Bad Credit Check");
        return loanUpdateRequest;
    }

    public static RegisterUserRequest buildFirstLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Mike");
        registerUserRequest.setLastName("Boyo");
        registerUserRequest.setEmail("mikebidemiboyo@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildSecondLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Bidemi");
        registerUserRequest.setLastName("Boyo");
        registerUserRequest.setEmail("bidemiboyo@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildThirdLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Bidemi");
        registerUserRequest.setLastName("Boyo");
        registerUserRequest.setEmail("helloboyo@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildFourthLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Jonathan");
        registerUserRequest.setLastName("Deji");
        registerUserRequest.setEmail("dejalltime@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildFifthLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Femi");
        registerUserRequest.setLastName("Oladeji");
        registerUserRequest.setEmail("djfemz@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildSixthLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Precious");
        registerUserRequest.setLastName("Onyeukwu");
        registerUserRequest.setEmail("presh@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildSeventhLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Grace");
        registerUserRequest.setLastName("Graceful");
        registerUserRequest.setEmail("grace@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildEighthLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Ibrahim");
        registerUserRequest.setLastName("Ibrahim");
        registerUserRequest.setEmail("ibra@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
    public static RegisterUserRequest buildNinthLoanOfficer() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();

        registerUserRequest.setFirstName("Kabiru");
        registerUserRequest.setLastName("Kaybee");
        registerUserRequest.setEmail("kb@gmail.com");
        registerUserRequest.setPassword("12BigWhiteFav@");
        registerUserRequest.setPhoneNumber("08066086234");
        registerUserRequest.setStreetNumber("18");
        registerUserRequest.setStreetName("Emily Akinola Street");
        registerUserRequest.setTown("Yaba");
        registerUserRequest.setState("Lagos");

        return registerUserRequest;
    }
}
