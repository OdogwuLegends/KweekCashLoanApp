package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerByEmail(String email);
}
