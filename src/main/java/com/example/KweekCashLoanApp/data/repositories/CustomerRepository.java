package com.example.KweekCashLoanApp.data.repositories;

import com.example.KweekCashLoanApp.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findCustomerByEmail(String email);
}
