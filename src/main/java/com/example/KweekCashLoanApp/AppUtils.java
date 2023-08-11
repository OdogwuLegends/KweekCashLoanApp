package com.example.KweekCashLoanApp;

import com.example.KweekCashLoanApp.data.enums.LoanStatus;
import com.example.KweekCashLoanApp.dtos.requests.LoanApplicationRequest;
import com.example.KweekCashLoanApp.dtos.requests.RegisterUserRequest;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    public static boolean emailIsCorrect(String email){
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean passwordIsCorrect(String password){
        String regex = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@%#$!?&]).{8,20})";
//        String regex = "/^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$/";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static String generateUniqueCode(int length){
        return generateRandomString(length);
    }

    private static String generateRandomString(int length){
        String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random RANDOM = new SecureRandom();
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static String generateRandomNumbers(int length){
        return generateNumbers(length);
    }

    private static String generateNumbers(int length){
        String numbers = "0123456789";
        Random random = new SecureRandom();
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return new String(returnValue);
    }

    public static String loanTenure(LoanApplicationRequest request){
        return switch (request.getLoanTenure()){
            case TWELVE_MONTHS -> "12 Months";
            case TWENTY_FOUR_MONTHS -> "24 Months";
            case THIRTY_SIX_MONTHS -> "36 Months";
            case FORTY_EIGHT_MONTHS -> "48 Months";
            case SIXTY_MONTHS -> "60 Months";
        };
    }

    public static String paymentMethod(LoanApplicationRequest request){
        return switch (request.getPaymentMethod()) {
            case CASH_PAYMENT -> "Cash Payment";
            case ONLINE_TRANSFER -> "Online Transfer";
            case STANDING_INSTRUCTION -> "Standing Instruction";
            case SALARY_DEDUCTIONS -> "Salary Deductions";
        };
    }
    public static String repaymentPreference(LoanApplicationRequest request){
        return switch (request.getRepaymentPreference()){
            case MONTHLY -> "Monthly";
            case QUARTERLY -> "Quarterly";
            case SEMI_ANNUALLY -> "Semi-Annually";
            case ANNUALLY -> "Annually";
        };
    }
    public static String loanStatus(LoanStatus loanStatus){
        return switch (loanStatus){
            case AWAITING_APPROVAL -> "AWAITING APPROVAL";
            case APPROVED -> "APPROVED";
            case REJECTED -> "REJECTED";
            case IN_PROGRESS -> "IN PROGRESS";
            case CLOSED -> "CLOSED";
        };
    }

    public static BigDecimal amountToPay(BigDecimal loanAmount, String loanTenure, double interestRate){
        BigDecimal result = BigDecimal.valueOf(interestRate).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
        BigDecimal interestAmount = result.multiply(loanAmount);
        BigDecimal totalPlusInterest = loanAmount.add(interestAmount);

        return switch (loanTenure) {
            case "12 Months" -> totalPlusInterest.divide(BigDecimal.valueOf(12),3, RoundingMode.HALF_UP);
            case "24 Months" -> totalPlusInterest.divide(BigDecimal.valueOf(24), 3,RoundingMode.HALF_UP);
            case "36 Months" -> totalPlusInterest.divide(BigDecimal.valueOf(36), 3,RoundingMode.HALF_UP);
            case "48 Months" -> totalPlusInterest.divide(BigDecimal.valueOf(48), 3,RoundingMode.HALF_UP);
            case "60 Months" -> totalPlusInterest.divide(BigDecimal.valueOf(60), 3,RoundingMode.HALF_UP);
            default -> new BigDecimal(0);
        };
    }
    public static LocalDate stringToLocalDate(String userInput) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(userInput, dateFormatter);
    }

    public static void validateDetails(RegisterUserRequest request) throws IncorrectDetailsException {
        if(request.getPhoneNumber().length() != 11 && request.getPhoneNumber().length() != 14){
            throw new IncorrectDetailsException("Password should be at least 8 characters long,\nshould contain uppercase letter,lowercase letter, a number and a special character");
        }
        if(!AppUtils.emailIsCorrect(request.getEmail())){
            throw new IncorrectDetailsException("Invalid email");
        }
        if(!AppUtils.passwordIsCorrect(request.getPassword())){
            throw new IncorrectDetailsException("Invalid password.");
        }
    }
}
