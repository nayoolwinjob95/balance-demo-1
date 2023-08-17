package com.jdc.balancedemo1.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jdc.balancedemo1.model.domain.entity.UserAccessLog;

public interface UserAccessLogRepo extends JpaRepository<UserAccessLog, Integer>, JpaSpecificationExecutor<UserAccessLog> {

}
