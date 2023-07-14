package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.ActiveLoans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ActiveLoansRepository extends JpaRepository<ActiveLoans,Long> {
    ActiveLoans findByCustomerId(Long id);
}
