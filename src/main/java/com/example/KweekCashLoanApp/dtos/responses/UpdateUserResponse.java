package com.example.KweekCashLoanApp.dtos.responses;

import lombok.Data;

@Data
public class UpdateUserResponse {
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
    private String message;
}
