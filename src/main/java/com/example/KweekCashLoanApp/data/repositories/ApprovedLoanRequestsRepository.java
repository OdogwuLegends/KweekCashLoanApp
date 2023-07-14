package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.ApprovedLoanRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovedLoanRequestsRepository extends JpaRepository<ApprovedLoanRequests,Long> {
    ApprovedLoanRequests findByUniqueCode(String code);
}
