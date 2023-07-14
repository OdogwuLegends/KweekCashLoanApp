package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.LoanOfficer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoanOfficerRepository extends JpaRepository<LoanOfficer, Long> {
        //THIS METHOD DIDNT WORK
//    Optional<LoanOfficer> findLoanOfficerByAdminLoginCode(String code);
    LoanOfficer findLoanOfficerByEmail(String email);

    LoanOfficer findLoanOfficerByAuthorizationCode(String code);

    boolean existsByAdminLoginCode(String adminLoginCode);

    boolean existsByAuthorizationCode (String authorizationCode);

    boolean existsByAdminLoginCodeAndAuthorizationCode(String adminLoginCode, String authorizationCode);
}
