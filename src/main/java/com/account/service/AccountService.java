package com.account.service;

import com.account.exception.AccountException;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;
import com.account.model.response.PlaceCountResponse;

public interface AccountService {

	public AccountResponse addAccount(AccountRequest accountRequest) throws AccountException, Exception;

	public AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest) throws AccountException, Exception;
	
	public void deleteAccount(String deleteAccountId) throws AccountException, Exception;
	
	public void changeStatus(String changeStatusAccountId, String changeStatus) throws AccountException, Exception;

	public PlaceCountResponse retrievePlaceCounts(String country) throws Exception;

	public boolean authenticateUser(String loginAccountId, String loginPin) throws AccountException, Exception;
}
