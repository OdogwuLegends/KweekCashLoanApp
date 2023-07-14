package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PendingLoanRequestsRepository extends JpaRepository<PendingLoanRequests, Long> {
    PendingLoanRequests findLoanRequestsByUniqueCode(String code);
}
