package com.jdc.balancedemo1.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jdc.balancedemo1.model.domain.entity.Balance.Type;
import com.jdc.balancedemo1.model.domain.entity.BalanceItem;

public interface BalanceItemRepo extends JpaRepository<BalanceItem, Integer>, JpaSpecificationExecutor<BalanceItem> {

	@Query("select sum(bi.unitPrice * bi.quantity) from BalanceItem bi where bi.balance.id < :id and bi.balance.type = :type")
	Optional<Number> getLastBalance(int id, Type type);

}
