package com.account.util;

import com.account.entity.AccountEntity;
import com.account.model.request.AccountRequest;
import com.account.model.response.AccountResponse;

public interface AccountMapperUtil {

	public AccountResponse generateResponseFromEntity(AccountEntity accountEntityResponse) throws Exception;

	public AccountEntity generateEntityFromRequest(AccountRequest accountRequest) throws Exception;
}
