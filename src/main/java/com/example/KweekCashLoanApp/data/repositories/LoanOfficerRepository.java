package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.LoanOfficer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Long> {
    LoanOfficer findLoanOfficerByEmail(String email);

    LoanOfficer findLoanOfficerByAuthorizationCode(String code);

    boolean existsByAdminLoginCode(String adminLoginCode);
    Optional<LoanOfficer> findLoanOfficerByAdminLoginCode(String adminLoginCode);
    Optional<LoanOfficer> findLoanOfficerByAdminLoginCodeAndAuthorizationCode(String adminLoginCode, String authorizationCode);
    boolean existsByAuthorizationCode (String authorizationCode);
    boolean existsByAdminLoginCodeAndAuthorizationCode(String adminLoginCode, String authorizationCode);
}
