package com.account.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.account.client.AccountClient;
import com.account.constant.AccountConstant;
import com.account.exception.AccountServiceException;
import com.account.model.client.PostalClientServiceResponse;
import com.account.model.request.AccountRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

@Service
public class AccountClientImpl implements AccountClient {

	Logger logger = LoggerFactory.getLogger(AccountClientImpl.class);

	public void postalServiceCall(AccountRequest accountRequest) throws AccountServiceException, Exception {
		RestTemplate restTemplate = new RestTemplate();
		Gson gson = new Gson();

		StringBuilder uri = new StringBuilder(AccountConstant.POSTAL_SERVICE_URI);
		String postalJsonString = null;
		PostalClientServiceResponse postalClientServiceResponse = null;

		// creating complete uri for postal service.
		uri.append(accountRequest.getCountry()).append("/").append(accountRequest.getPostalcode());

		try {
			postalJsonString = restTemplate.getForObject(uri.toString(), String.class);
		} catch (Exception ex) {
			logger.error("Error while accessing Postal service : " + ex);
			throw new AccountServiceException(AccountConstant.WRONG_RESPONSE_FROM_POSTAL_SERVICE + uri.toString());
		}

		postalJsonString = postalJsonString.replaceAll(" ", "");
		logger.info("Rsponse from Postal service : " + postalJsonString);

		try {
			postalClientServiceResponse = gson.fromJson(postalJsonString, PostalClientServiceResponse.class);
		} catch (JsonParseException ex) {
			logger.error("Error while parsing postal service response : " + ex);
			throw new AccountServiceException(AccountConstant.WRONG_RESPONSE_FROM_POSTAL_SERVICE + uri.toString());
		}

		if (postalClientServiceResponse == null || postalClientServiceResponse.getPlaces() == null
				|| postalClientServiceResponse.getPlaces().size() == 0
				|| postalClientServiceResponse.getPlaces().get(0) == null) {
			logger.error("Getting wrong response from postal service.");
			throw new AccountServiceException(AccountConstant.WRONG_RESPONSE_FROM_POSTAL_SERVICE + uri.toString());
		}
		accountRequest.setLatitude(postalClientServiceResponse.getPlaces().get(0).getLatitude());
		accountRequest.setLongitude(postalClientServiceResponse.getPlaces().get(0).getLongitude());
		accountRequest.setPlace(postalClientServiceResponse.getPlaces().get(0).getPlacename());
		accountRequest.setState(postalClientServiceResponse.getPlaces().get(0).getState());
	}
}
