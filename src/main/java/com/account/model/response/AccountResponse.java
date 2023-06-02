package com.account.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {

	private String accountId;
	private String securityPin;
	private String status;

}
