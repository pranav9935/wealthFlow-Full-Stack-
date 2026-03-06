package com.wealthmanager.repository;

import com.wealthmanager.entity.Investment;
import com.wealthmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUser(User user);
    Optional<Investment> findByUserAndStockSymbol(User user, String stockSymbol); 
}