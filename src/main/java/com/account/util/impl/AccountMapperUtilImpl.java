package com.account.util.impl;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.account.entity.AccountEntity;
import com.account.model.request.AccountRequest;
import com.account.model.response.AccountResponse;
import com.account.util.AccountMapperUtil;

import net.bytebuddy.utility.RandomString;

@Service
public class AccountMapperUtilImpl implements AccountMapperUtil {

	public AccountResponse generateResponseFromEntity(AccountEntity accountEntityResponse) throws Exception {
		AccountResponse accountResponse = new AccountResponse();
		accountResponse.setAccountId(accountEntityResponse.getAccountId());
		accountResponse.setSecurityPin(accountEntityResponse.getSecuritypin());
		accountResponse.setStatus(accountEntityResponse.getStatus());
		return accountResponse;
	}

	public AccountEntity generateEntityFromRequest(AccountRequest accountRequest) throws Exception {
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setAge(accountRequest.getAge());
		accountEntity.setCountry(accountRequest.getCountry());
		accountEntity.setEmail(accountRequest.getEmail());
		accountEntity.setLatitude(accountRequest.getLatitude());
		accountEntity.setLongitude(accountRequest.getLongitude());
		accountEntity.setName(accountRequest.getName());
		accountEntity.setPlace(accountRequest.getPlace());
		accountEntity.setPostalcode(accountRequest.getPostalcode());
		accountEntity.setState(accountRequest.getState());
		accountEntity.setStatus(accountRequest.getStatus());

		// Generate Account Id : Unique 6 alpha
		String sixAlphaNumericString = new RandomString(6, ThreadLocalRandom.current()).nextString();
		accountEntity.setAccountId(sixAlphaNumericString);

		// Generate 4 digit Security PIN.
		generatePin(accountEntity);

		return accountEntity;
	}

	private void generatePin(AccountEntity accountEntity) {
		Random random = new Random();
		int randomInt = random.nextInt(9);
		String securitypin = "";
		while (securitypin.length() < 4) {
			randomInt = random.nextInt(9);
			String addition = String.valueOf(randomInt);
			if (securitypin.contains(addition))
				continue;
			securitypin += addition;
		}
		accountEntity.setSecuritypin(securitypin);
	}
}
