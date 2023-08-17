package com.jdc.balancedemo1.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jdc.balancedemo1.model.domain.entity.Balance;

public interface BalanceRepo extends JpaRepository<Balance, Integer>, JpaSpecificationExecutor<Balance> {

}
