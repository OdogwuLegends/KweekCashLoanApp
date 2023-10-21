package com.example.KweekCashLoanApp.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.example.KweekCashLoanApp.utils.HardcodedValues.LOAN_OFFICERS;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = LOAN_OFFICERS)
public class LoanOfficer{
    @Id
    @GeneratedValue
    private Long loanOfficerId;

    @Column(nullable = false,length = 100)
    private String firstName;

    @Column(nullable = false,length = 100)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String streetNumber;

    @Column(nullable = false, length = 100)
    private String streetName;

    @Column(nullable = false, length = 100)
    private String town;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 100)
    private String authorizationCode;

    @Column(nullable = false)
    private String adminLoginCode;

    @Column(nullable = false)
    private LocalDate dateRegistered;
}
