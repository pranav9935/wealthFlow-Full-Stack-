package com.wealthmanager.repository;

import com.wealthmanager.entity.Investment;
import com.wealthmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUser(User user);

}