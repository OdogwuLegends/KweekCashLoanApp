package com.example.KweekCashLoanApp.dtos.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String adminLoginCode;
}
