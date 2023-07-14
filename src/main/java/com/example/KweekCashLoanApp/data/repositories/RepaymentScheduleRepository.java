package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.RepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule,Long> {
}
