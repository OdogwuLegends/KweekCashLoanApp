package com.example.KweekCashLoanApp.dtos.responses;

import lombok.Data;

@Data
public class FindUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String occupation;
    private String address;
    private long id;
}
