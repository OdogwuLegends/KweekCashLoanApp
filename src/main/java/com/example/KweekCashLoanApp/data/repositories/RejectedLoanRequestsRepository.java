package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.RejectedLoanRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectedLoanRequestsRepository extends JpaRepository<RejectedLoanRequests,Long> {
}
