package com.account.client.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.account.exception.AccountServiceException;
import com.account.model.request.AccountRequest;
import com.account.util.UtilTest;

@SpringBootTest
public class AccountClientImplTest {

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	AccountClientImpl testServiceImpl;

	UtilTest utilTest = new UtilTest();

	@Test
	void postalServiceCallTest() throws AccountServiceException, Exception {

		String strpostalClientServiceResponse = "{\"post code\": \"06001\", \"country\": \"United States\", \"country abbreviation\": \"US\", \"places\": [{\"place name\": \"Avon\", \"longitude\": \"-72.8653\", \"state\": \"Connecticut\", \"state abbreviation\": \"CT\", \"latitude\": \"41.7905\"}]}";

		AccountRequest accountRequest = utilTest.generateAccountRequest();

		when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(strpostalClientServiceResponse);

		testServiceImpl.postalServiceCall(accountRequest);

		assertEquals("-72.8653", accountRequest.getLongitude());

	}

}
