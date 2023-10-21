package com.example.KweekCashLoanApp.dtos.responses;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String authorizationCode;
    private String adminLoginCode;
    private String phoneNumber;
    private String streetNumber;
    private String streetName;
    private String town;
    private String state;
    private String message;
    private Long customerId;
    private Long loanOfficerId;



    @Override
    public String toString(){
        return "Welcome "+firstName + " "+lastName+". Your account creation was successful.";
    }
}
