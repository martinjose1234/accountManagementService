package com.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import com.account.client.AccountClient;
import com.account.entity.AccountEntity;
import com.account.exception.AccountException;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;
import com.account.model.response.PlaceCountResponse;
import com.account.repository.AccountRepository;
import com.account.util.AccountMapperUtil;
import com.account.util.UtilTest;

@SpringBootTest
public class AccountServiceImplTest {

	@Mock
	Logger logger;

	@Mock
	AccountRepository accountRepository;

	@Mock
	AccountClient accountClient;

	@Mock
	AccountMapperUtil accountMapperUtil;

	@InjectMocks
	AccountServiceImpl testServiceImpl;
	
	UtilTest utilTest = new UtilTest();

	@Test
	void authenticateUserTest() throws AccountException, Exception {

		List<AccountEntity> accounts = utilTest.generateAccountEntityList();

		when(accountRepository.findByAccountIdAndSecuritypin(any(String.class), any(String.class)))
				.thenReturn(accounts);

		boolean authStatus = testServiceImpl.authenticateUser("UqNJo6", "0453");

		assertEquals(true, authStatus);
	}

	@Test
	void addAccountTest() throws AccountException, Exception {

		List<AccountEntity> accounts = utilTest.generateAccountEntityList();
		AccountRequest accountRequest = utilTest.generateAccountRequest();
		AccountResponse expectedAccountResponse = utilTest.generateAccountResponse();

		// To Mock void method.
		doNothing().when(accountClient).postalServiceCall(any(AccountRequest.class));

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		when(accountMapperUtil.generateEntityFromRequest(any(AccountRequest.class))).thenReturn(accounts.get(0));
		when(accountMapperUtil.generateResponseFromEntity(any(AccountEntity.class)))
				.thenReturn(expectedAccountResponse);

		when(accountRepository.save(any(AccountEntity.class))).thenReturn(accounts.get(0));

		AccountResponse accountResponse = testServiceImpl.addAccount(accountRequest);

		assertEquals(expectedAccountResponse, accountResponse);
	}

	@Test
	void updateAccountTest() throws AccountException, Exception {

		List<AccountEntity> accounts = utilTest.generateAccountEntityList();
		UpdateAccountRequest updateAccountRequest = utilTest.generateUpdateAccountRequest();
		AccountResponse expectedAccountResponse = utilTest.generateAccountResponse();

		// To Mock void method.
		doNothing().when(accountClient).postalServiceCall(any(AccountRequest.class));

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		when(accountMapperUtil.generateEntityFromRequest(any(AccountRequest.class))).thenReturn(accounts.get(0));
		when(accountMapperUtil.generateResponseFromEntity(any(AccountEntity.class)))
				.thenReturn(expectedAccountResponse);

		when(accountRepository.save(any(AccountEntity.class))).thenReturn(accounts.get(0));
		when(accountRepository.findByAccountId(any(String.class))).thenReturn(accounts);

		AccountResponse accountResponse = testServiceImpl.updateAccount(updateAccountRequest);

		assertEquals(expectedAccountResponse, accountResponse);
	}

	@Test
	void deleteAccountTest() throws AccountException, Exception {

		List<AccountEntity> accounts = utilTest.generateAccountEntityList();
		UpdateAccountRequest updateAccountRequest = utilTest.generateUpdateAccountRequest();
		AccountResponse expectedAccountResponse = utilTest.generateAccountResponse();

		// To Mock void method.
		doNothing().when(accountClient).postalServiceCall(any(AccountRequest.class));

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		when(accountMapperUtil.generateEntityFromRequest(any(AccountRequest.class))).thenReturn(accounts.get(0));
		when(accountMapperUtil.generateResponseFromEntity(any(AccountEntity.class)))
				.thenReturn(expectedAccountResponse);
		accounts.remove(0);
		when(accountRepository.findByAccountId(any(String.class))).thenReturn(accounts);

		testServiceImpl.deleteAccount("UqNJo6");
	}

	@Test
	void changeStatusTest() throws AccountException, Exception {

		List<AccountEntity> accounts = utilTest.generateAccountEntityList();
		AccountResponse expectedAccountResponse = utilTest.generateAccountResponse();

		// To Mock void method.
		doNothing().when(accountClient).postalServiceCall(any(AccountRequest.class));

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		when(accountMapperUtil.generateEntityFromRequest(any(AccountRequest.class))).thenReturn(accounts.get(0));
		when(accountMapperUtil.generateResponseFromEntity(any(AccountEntity.class)))
				.thenReturn(expectedAccountResponse);
		accounts.remove(0);
		when(accountRepository.findByAccountId(any(String.class))).thenReturn(accounts);

		testServiceImpl.changeStatus("UqNJo6", "Active");
	}

	@Test
	void retrievePlaceCountsTest() throws AccountException, Exception {

		List<AccountEntity> accounts = utilTest.generateAccountEntityList();

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		when(accountRepository.findByCountryEqualsOrderByStateAsc(any(String.class))).thenReturn(accounts);

		PlaceCountResponse PlaceCountResponse = testServiceImpl.retrievePlaceCounts("US");

		assertNotNull(PlaceCountResponse);
	}

	

}
