package com.account.client;

import com.account.exception.AccountServiceException;
import com.account.model.request.AccountRequest;

public interface AccountClient {
	
	
	public void postalServiceCall(AccountRequest accountRequest) throws AccountServiceException, Exception;

}
