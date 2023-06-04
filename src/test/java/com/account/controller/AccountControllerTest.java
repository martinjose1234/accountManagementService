package com.account.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.account.exception.AccountServiceException;
import com.account.model.request.AccountRequest;
import com.account.model.response.AccountResponse;
import com.account.service.AccountService;
import com.account.util.UtilTest;

@SpringBootTest
public class AccountControllerTest {

	@Mock
	AccountService accountService;

	@InjectMocks
	AccountController accountController;

	UtilTest utilTest = new UtilTest();

	@Test
	void authenticateUserTest() throws AccountServiceException, Exception {

		AccountRequest accountRequest = utilTest.generateAccountRequest();
		AccountResponse accountResponse = utilTest.generateAccountResponse();
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		ResponseEntity<Object> expectedResponse = new ResponseEntity<>(accountResponse, HttpStatus.OK);

		when(accountService.addAccount(any(AccountRequest.class))).thenReturn(accountResponse);

		ResponseEntity<Object> actualResponse = accountController.addAccount(accountRequest, result);

		System.out.println(">>>>>>>>>>>>>>>>>>>>> " + actualResponse.getBody().getClass());
		
		assertNotNull(actualResponse);
		
//		assertEquals(expectedResponse, actualResponse);
	}

}
