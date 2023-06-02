package com.account.client;

import com.account.exception.AccountException;
import com.account.model.request.AccountRequest;

public interface AccountClient {
	
	
	public void postalServiceCall(AccountRequest accountRequest) throws AccountException, Exception;

}
