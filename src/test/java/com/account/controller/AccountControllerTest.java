package com.account.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.account.exception.AccountServiceException;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;
import com.account.model.response.SuccessResponse;
import com.account.service.AccountService;
import com.account.util.UtilTest;

@SpringBootTest
public class AccountControllerTest {

	@Mock
	AccountService accountService;

	@Mock
	Logger logger;

	@InjectMocks
	AccountController accountController;

	UtilTest utilTest = new UtilTest();

	@Test
	void addAccountTest() throws AccountServiceException, Exception {

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		AccountRequest accountRequest = utilTest.generateAccountRequest();
		AccountResponse accountResponse = utilTest.generateAccountResponse();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(false);

		ResponseEntity<Object> expectedResponse = new ResponseEntity<>(accountResponse, HttpStatus.OK);

		when(accountService.addAccount(any(AccountRequest.class))).thenReturn(accountResponse);

		ResponseEntity<Object> actualResponse = accountController.addAccount(accountRequest, result);

		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	void updateAccountTest() throws AccountServiceException, Exception {

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		AccountResponse accountResponse = utilTest.generateAccountResponse();
		UpdateAccountRequest updateAccountRequest = utilTest.generateUpdateAccountRequest();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(false);

		ResponseEntity<Object> expectedResponse = new ResponseEntity<>(accountResponse, HttpStatus.OK);

		when(accountService.authenticateUser(any(String.class), any(String.class))).thenReturn(true);
		when(accountService.updateAccount(any(UpdateAccountRequest.class))).thenReturn(accountResponse);

		ResponseEntity<Object> actualResponse = accountController.updateAccount(updateAccountRequest, result, "sedrft",
				"1234");

		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	void deleteAccountTest() throws AccountServiceException, Exception {

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(false);

		when(accountService.authenticateUser(any(String.class), any(String.class))).thenReturn(true);

		// Do Nothing:
		doNothing().when(accountService).deleteAccount(any(String.class));

		ResponseEntity<Object> actualResponse = accountController.deleteAccount("uhjytr", "1234", "sedrft");

		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("sedrft" + " successfuly deleted.");

		ResponseEntity<Object> expectedResponse = new ResponseEntity<>(successResponse, HttpStatus.OK);

		SuccessResponse actualResponseResponse = (SuccessResponse) actualResponse.getBody();
		SuccessResponse expectedResponseResponse = (SuccessResponse) expectedResponse.getBody();

		assertEquals(actualResponseResponse.getMessage(), expectedResponseResponse.getMessage());
	}

	@Test
	void changeStatusTest() throws AccountServiceException, Exception {

		// Mock Logger.info
		doNothing().when(logger).info(any(String.class));

		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(false);

		when(accountService.authenticateUser(any(String.class), any(String.class))).thenReturn(true);

		// Do Nothing:
		doNothing().when(accountService).changeStatus(any(String.class), any(String.class));

		ResponseEntity<Object> actualResponse = accountController.changeStatus("asdcsd", "1234", "sedrft", "Inactive");

		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("Status of " + "sedrft" + " successfuly updated.");

		ResponseEntity<Object> expectedResponse = new ResponseEntity<>(successResponse, HttpStatus.OK);

		SuccessResponse actualResponseResponse = (SuccessResponse) actualResponse.getBody();
		SuccessResponse expectedResponseResponse = (SuccessResponse) expectedResponse.getBody();

		assertEquals(actualResponseResponse.getMessage(), expectedResponseResponse.getMessage());
	}

}
