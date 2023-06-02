package com.account.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.account.constant.AccountConstant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountRequest {

	@NotNull(message = AccountConstant.EMAIL_MESSAGE)
	@NotEmpty(message = AccountConstant.EMAIL_MESSAGE)
	@Email(regexp = AccountConstant.EMAIL_REGEX_EXPRESSION, message = AccountConstant.EMAIL_INVALID_MESSAGE)
	private String email;

	@NotNull(message = AccountConstant.NAME_MESSAGE)
	@NotEmpty(message = AccountConstant.NAME_MESSAGE)
	private String name;

	@NotNull(message = AccountConstant.COUNTRY_MESSAGE)
	@NotEmpty(message = AccountConstant.COUNTRY_MESSAGE)
	@Pattern(regexp = "(US|DE|ES|FR)")
	private String country;

	@NotNull(message = AccountConstant.POSTALCODE_MESSAGE)
	@NotEmpty(message = AccountConstant.POSTALCODE_MESSAGE)
	private String postalcode;

	@NotNull(message = AccountConstant.AGE_MESSAGE)
	private Integer age;

	@NotNull(message = AccountConstant.STATUS_MESSAGE)
	@NotEmpty(message = AccountConstant.STATUS_MESSAGE)
	@Pattern(regexp = "(Requested|Active|Inactive)")
	private String status;

	private String place;

	private String state;

	private String longitude;

	private String latitude;

}