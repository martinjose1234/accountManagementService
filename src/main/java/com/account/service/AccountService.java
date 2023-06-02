package com.account.service;

import com.account.exception.AccountServiceException;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;
import com.account.model.response.PlaceCountResponse;

public interface AccountService {

	public AccountResponse addAccount(AccountRequest accountRequest) throws AccountServiceException, Exception;

	public AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest) throws AccountServiceException, Exception;
	
	public void deleteAccount(String deleteAccountId) throws AccountServiceException, Exception;
	
	public void changeStatus(String changeStatusAccountId, String changeStatus) throws AccountServiceException, Exception;

	public PlaceCountResponse retrievePlaceCounts(String country) throws Exception;

	public boolean authenticateUser(String loginAccountId, String loginPin) throws AccountServiceException, Exception;
}
