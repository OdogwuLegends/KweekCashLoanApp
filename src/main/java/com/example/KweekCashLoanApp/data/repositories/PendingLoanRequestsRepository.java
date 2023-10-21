package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.PendingLoanRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PendingLoanRequestsRepository extends JpaRepository<PendingLoanRequests, Long> {
    Optional<PendingLoanRequests> findLoanRequestsByUniqueCode(String code);
}
