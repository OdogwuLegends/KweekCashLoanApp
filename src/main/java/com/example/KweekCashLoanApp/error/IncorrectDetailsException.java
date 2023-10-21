package com.example.KweekCashLoanApp.error;

public class IncorrectDetailsException extends RuntimeException{
    public IncorrectDetailsException(String message){
        super(message);
    }
}
