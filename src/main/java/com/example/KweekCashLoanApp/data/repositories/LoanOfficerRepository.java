package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.LoanOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Long> {
    Optional<LoanOfficer> findLoanOfficerByEmail(String email);
    Optional<LoanOfficer> findLoanOfficerByAdminLoginCode(String adminLoginCode);
    Optional<LoanOfficer> findLoanOfficerByAdminLoginCodeAndAuthorizationCode(String adminLoginCode, String authorizationCode);
}
