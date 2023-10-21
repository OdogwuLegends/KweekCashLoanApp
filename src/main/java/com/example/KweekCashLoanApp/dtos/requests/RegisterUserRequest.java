package com.example.KweekCashLoanApp.dtos.requests;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String occupation;
    private String streetNumber;
    private String streetName;
    private String town;
    private String state;
}
