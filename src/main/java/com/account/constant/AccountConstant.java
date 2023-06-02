package com.account.constant;

public class AccountConstant {

	
	
	public static final String JSON_CONTENT_TYPE = "application/json";
	
	
	/**
	 * 
	 * Service paths
	 * 
	 */

	public static final String BASE_PATH = "/accountService";
	public static final String ADD_ACCOUNT = "/addAccount";
	public static final String UPDATE_ACCOUNT = "/updateAccount";
	public static final String DELETE_ACCOUNT = "/deleteAccount";
	public static final String CHANGE_STATUS = "/changeStatus";
	public static final String RETRIEVE_COUNT = "/retrievePlaceCounts/{country}";

	/**
	 * 
	 * Client service uri
	 * 
	 */

	public static final String POSTAL_SERVICE_URI = "https://api.zippopotam.us/";

	/**
	 * 
	 * Error Messages.
	 * 
	 */

	public static final String WRONG_RESPONSE_FROM_POSTAL_SERVICE = "Postal service is unavailable or Wrong response from the postal service: ";
	public static final String ACCOUNT_NOT_EXIST = "Given account id is not existing in the system.";

	/**
	 * 
	 * Validation messages
	 * 
	 */

	public static final String EMAIL_MESSAGE = "Please provide an email";

	public static final String ACCOUNTID_MESSAGE = "Please provide an account Id";

	public static final String EMAIL_REGEX_EXPRESSION = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";

	public static final String COUNTRY_REGEX_EXPRESSION = "(US|DE|ES|FR)";

	public static final String STATUS_REGEX_EXPRESSION = "(Requested|Active|Inactive)";

	public static final String EMAIL_INVALID_MESSAGE = "Email is invalid";

	public static final String EMAIL_EXISTS_MESSAGE = "Email already exists";

	public static final String NAME_MESSAGE = "Please provide Name.";

	public static final String COUNTRY_MESSAGE = "Please provide Country.";

	public static final String STATUS_MESSAGE = "Please provide Status.";

	public static final String POSTALCODE_MESSAGE = "Please provide PostalCode.";

	public static final String AGE_MESSAGE = "Please provide age.";

}
