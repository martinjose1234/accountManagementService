package com.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.account.entity.AccountEntity;

@Repository
public interface AccountRepository
		extends JpaRepository<AccountEntity, String>, JpaSpecificationExecutor<AccountEntity> {

	List<AccountEntity> findByCountryEqualsOrderByStateAsc(String country);

	List<AccountEntity> findByAccountId(String accountId);

	List<AccountEntity> findByAccountIdAndSecuritypin(String loginAccountId, String loginPin);

}
