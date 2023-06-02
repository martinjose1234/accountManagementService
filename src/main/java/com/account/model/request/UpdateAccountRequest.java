package com.account.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.account.constant.AccountConstant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAccountRequest {

	@NotNull(message = AccountConstant.ACCOUNTID_MESSAGE)
	@NotEmpty(message = AccountConstant.ACCOUNTID_MESSAGE)
	private String accountId;

	private String email;

	private String name;

	private String country;

	private String postalcode;

	private Integer age;

	private String status;

}
