package com.account.util;

import java.util.ArrayList;
import java.util.List;

import com.account.entity.AccountEntity;
import com.account.model.client.Place;
import com.account.model.client.PostalClientServiceResponse;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;

public class UtilTest {

	public AccountRequest generateAccountRequest() {
		AccountRequest accountRequest = new AccountRequest();
		accountRequest.setAge(22);
		accountRequest.setCountry("US");
		accountRequest.setEmail("John@gmail.com");
		accountRequest.setEmail("john@gmail.com");
		accountRequest.setLatitude("34.209");
		accountRequest.setLongitude("-91.9859");
		accountRequest.setName("John");
		accountRequest.setPlace("PineBluff");
		accountRequest.setPostalcode("06001");
		accountRequest.setState("Arkansas");
		accountRequest.setStatus("Active");
		return accountRequest;
	}

	public UpdateAccountRequest generateUpdateAccountRequest() {
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

	public List<AccountEntity> generateAccountEntityList() {
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

	public AccountResponse generateAccountResponse() {
		AccountResponse accountResponse = new AccountResponse();
		accountResponse.setAccountId("UqNJo6");
		accountResponse.setSecurityPin("1234");
		accountResponse.setStatus("Active");
		return accountResponse;
	}

	public PostalClientServiceResponse generatePostalResponse() {
		PostalClientServiceResponse postalClientServiceResponse = new PostalClientServiceResponse();
		postalClientServiceResponse.setCountry("United States");
		postalClientServiceResponse.setCountryabbreviation("US");
		postalClientServiceResponse.setPostcode("06001");
		Place place = new Place();
		place.setLatitude("41.7905");
		place.setLongitude("-72.8653");
		place.setPlacename("Avon");
		place.setState("Connecticut");
		place.setStateabbreviation("CT");
		List<Place> places = new ArrayList<>();
		places.add(place);
		postalClientServiceResponse.setPlaces(places);

		return postalClientServiceResponse;
	}
}
