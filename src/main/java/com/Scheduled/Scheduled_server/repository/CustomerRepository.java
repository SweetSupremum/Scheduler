package com.Scheduled.Scheduled_server.repository;

import com.Scheduled.Scheduled_server.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserName(String userName);
}
