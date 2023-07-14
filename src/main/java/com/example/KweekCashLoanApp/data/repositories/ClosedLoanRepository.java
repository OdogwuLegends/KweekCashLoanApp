package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.ClosedLoans;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClosedLoanRepository extends JpaRepository<ClosedLoans,Long>{
}
