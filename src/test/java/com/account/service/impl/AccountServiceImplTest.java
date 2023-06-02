package com.account.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

	@Test
	void authenticateUserTest() throws AccountException, Exception {

		List<AccountEntity> accounts = generateAccountEntityList();

		when(accountRepository.findByAccountIdAndSecuritypin(any(String.class), any(String.class)))
				.thenReturn(accounts);

		boolean authStatus = testServiceImpl.authenticateUser("UqNJo6", "0453");

		assertEquals(true, authStatus);
	}

	@Test
	void addAccountTest() throws AccountException, Exception {

		List<AccountEntity> accounts = generateAccountEntityList();
		AccountRequest accountRequest = generateAccountRequest();
		AccountResponse expectedAccountResponse = generateAccountResponse();

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

		List<AccountEntity> accounts = generateAccountEntityList();
		UpdateAccountRequest updateAccountRequest = generateUpdateAccountRequest();
		AccountResponse expectedAccountResponse = generateAccountResponse();

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

		List<AccountEntity> accounts = generateAccountEntityList();
		UpdateAccountRequest updateAccountRequest = generateUpdateAccountRequest();
		AccountResponse expectedAccountResponse = generateAccountResponse();

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

		List<AccountEntity> accounts = generateAccountEntityList();
		UpdateAccountRequest updateAccountRequest = generateUpdateAccountRequest();
		AccountResponse expectedAccountResponse = generateAccountResponse();

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

		List<AccountEntity> accounts = generateAccountEntityList();

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		when(accountRepository.findByCountryEqualsOrderByStateAsc(any(String.class))).thenReturn(accounts);

		PlaceCountResponse PlaceCountResponse = testServiceImpl.retrievePlaceCounts("US");

		assertNotNull(PlaceCountResponse);
	}

	private AccountRequest generateAccountRequest() {
		AccountRequest accountRequest = new AccountRequest();
		accountRequest.setAge(22);
		accountRequest.setCountry("US");
		accountRequest.setEmail("John@gmail.com");
		accountRequest.setEmail("john@gmail.com");
		accountRequest.setLatitude("34.209");
		accountRequest.setLongitude("-91.9859");
		accountRequest.setName("John");
		accountRequest.setPlace("PineBluff");
		accountRequest.setPostalcode("71601");
		accountRequest.setState("Arkansas");
		accountRequest.setStatus("Active");
		return accountRequest;
	}

	private UpdateAccountRequest generateUpdateAccountRequest() {
		UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
		updateAccountRequest.setAccountId("UqNJo6");
		updateAccountRequest.setAge(22);
		updateAccountRequest.setCountry("US");
		updateAccountRequest.setEmail("abcd@gmail.com");
		updateAccountRequest.setName("John");
		updateAccountRequest.setPostalcode("71601");
		updateAccountRequest.setStatus("Active");
		return updateAccountRequest;
	}

	private List<AccountEntity> generateAccountEntityList() {
		List<AccountEntity> accounts = new ArrayList<>();
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setAccountId("UqNJo6");
		accountEntity.setAge(20);
		accountEntity.setCountry("US");
		accountEntity.setEmail("john@gmail.com");
		accountEntity.setSecuritypin("1234");
		accountEntity.setLatitude("34.209");
		accountEntity.setLongitude("-91.9859");
		accountEntity.setName("John");
		accountEntity.setPlace("PineBluff");
		accountEntity.setPostalcode("71601");
		accountEntity.setSecuritypin("6018");
		accountEntity.setState("Arkansas");
		accountEntity.setStatus("Active");
		accounts.add(accountEntity);

		accountEntity = new AccountEntity();
		accountEntity.setAccountId("SgQJC4");
		accountEntity.setAge(50);
		accountEntity.setCountry("US");
		accountEntity.setEmail("ram@gmail.com");
		accountEntity.setLatitude("56.209");
		accountEntity.setLongitude("-67.9859");
		accountEntity.setName("ram");
		accountEntity.setPlace("Avon");
		accountEntity.setPostalcode("06001");
		accountEntity.setSecuritypin("6235");
		accountEntity.setState("Connecticut");
		accountEntity.setStatus("Inactive");
		accounts.add(accountEntity);
		return accounts;
	}

	private AccountResponse generateAccountResponse() {
		AccountResponse accountResponse = new AccountResponse();
		accountResponse.setAccountId("UqNJo6");
		accountResponse.setSecurityPin("1234");
		accountResponse.setStatus("Active");
		return accountResponse;
	}

}
