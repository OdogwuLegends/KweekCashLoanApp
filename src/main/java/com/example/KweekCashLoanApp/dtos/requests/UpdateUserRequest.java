package com.example.KweekCashLoanApp.dtos.requests;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;       //Remember this field
    private String newEmail; //Remember this field
    private String password;
    private String newPassword;
    private String phoneNumber;
    private String occupation;
    private String streetNumber;
    private String streetName;
    private String town;
    private String state;
}
