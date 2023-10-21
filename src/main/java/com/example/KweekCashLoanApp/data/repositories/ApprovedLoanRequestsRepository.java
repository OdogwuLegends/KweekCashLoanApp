package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.ApprovedLoanRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovedLoanRequestsRepository extends JpaRepository<ApprovedLoanRequests,Long> {
    Optional<ApprovedLoanRequests> findByUniqueCode(String code);
}
