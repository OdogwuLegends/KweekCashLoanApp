package com.example.KweekCashLoanApp.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Data
//@Builder
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
