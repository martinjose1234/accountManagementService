package com.account.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.account.constant.AccountConstant;
import com.account.exception.AccountServiceException;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;
import com.account.model.response.ErrorResponse;
import com.account.model.response.PlaceCountResponse;
import com.account.model.response.SuccessResponse;
import com.account.service.AccountService;



/**
 * 
 * NOTE : 
 * 
 * SPRING BOOT INPUT VALIDATION  : Please Refer ->  1. @Valid, 2. BindingResult, 3. // SPRING-BOOT REQUEST VALIDATION :
 * 
 * SPRING BOOT EXCEPTION HANDLER : Please Refer ->  1. ExceptionHandlerAdviceController.java , 2. <just throws ur exceptions in all api methods in proper hierarchy> (throws AccountServiceException, Exception)
 *
 * NOTE : In exception handling, api should always return ur customized return message. (here it is ErrorResponse.java) 
 *
 */



@RestController
@RequestMapping(path = AccountConstant.BASE_PATH)
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	AccountService accountService;

	ErrorResponse errorResponse;
	AccountResponse accountResponse;

	/**
	 * 
	 * To create an account for a customer
	 *
	 * @param accountRequest input request
	 * 
	 * @param result         binding result
	 * 
	 * @return accountResponse
	 * 
	 */
	@PostMapping(path = AccountConstant.ADD_ACCOUNT, consumes = AccountConstant.JSON_CONTENT_TYPE, produces = AccountConstant.JSON_CONTENT_TYPE)
	public ResponseEntity<Object> addAccount(@RequestBody @Valid AccountRequest accountRequest, BindingResult result)
			throws AccountServiceException, Exception {

		// SPRING-BOOT REQUEST VALIDATION :
		if (result.hasErrors()) {
			return requestValidation(result);
		}

		accountResponse = accountService.addAccount(accountRequest);

		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
	}

	private ResponseEntity<Object> requestValidation(BindingResult result) {
		errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
		String message = result.getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.collect(Collectors.joining("; "));
		errorResponse.setMessage(message);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * To update an account for a customer
	 *
	 * @param accountRequest input request
	 * 
	 * @param result         binding result
	 * 
	 * @param loginAccountId logged user account id.
	 * 
	 * @param loginPin       logged user secure pin id.
	 * 
	 * @return accountResponse
	 * 
	 */
	@PutMapping(path = AccountConstant.UPDATE_ACCOUNT, consumes = AccountConstant.JSON_CONTENT_TYPE, produces = AccountConstant.JSON_CONTENT_TYPE)
	public ResponseEntity<Object> updateAccount(@RequestBody @Valid UpdateAccountRequest updateAccountRequest,
			BindingResult result, @RequestHeader("loginAccountId") String loginAccountId,
			@RequestHeader("loginSecurePin") String loginSecurePin) throws AccountServiceException, Exception {

		// SPRING-BOOT REQUEST VALIDATION :
		if (result.hasErrors()) {
			return requestValidation(result);
		}

		// Authenticate user.
		boolean authentication = accountService.authenticateUser(loginAccountId, loginSecurePin);
		if (!authentication) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Authentication failed. ");
			errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
		}

		accountResponse = accountService.updateAccount(updateAccountRequest);

		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * To delete an inactive account
	 *
	 * 
	 * @param loginAccountId logged user account id.
	 * 
	 * @param loginPin       logged user secure pin id.
	 * 
	 * @return successResponse
	 * 
	 */
	@DeleteMapping(path = AccountConstant.DELETE_ACCOUNT, produces = AccountConstant.JSON_CONTENT_TYPE)
	public ResponseEntity<Object> deleteAccount(@RequestHeader("loginAccountId") String loginAccountId,
			@RequestHeader("loginSecurePin") String loginSecurePin, @RequestParam String deleteAccountId)
			throws AccountServiceException, Exception {

		// Authenticate user.
		boolean authentication = accountService.authenticateUser(loginAccountId, loginSecurePin);
		if (!authentication) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Authentication failed. ");
			errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
		}

		accountService.deleteAccount(deleteAccountId);

		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage(deleteAccountId + " successfuly deleted.");

		return new ResponseEntity<>(successResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * To change the status of an account
	 *
	 * 
	 * @param loginAccountId logged user account id.
	 * 
	 * @param loginPin       logged user secure pin id.
	 * 
	 * @param changeStatus   the new status.
	 * 
	 * @return successResponse
	 * 
	 */
	@PutMapping(path = AccountConstant.CHANGE_STATUS, produces = AccountConstant.JSON_CONTENT_TYPE)
	public ResponseEntity<Object> changeStatus(@RequestHeader("loginAccountId") String loginAccountId,
			@RequestHeader("loginSecurePin") String loginSecurePin, @RequestParam String changeStatusAccountId,
			@RequestParam String changeStatus) throws AccountServiceException, Exception {

		// Authenticate user.
		boolean authentication = accountService.authenticateUser(loginAccountId, loginSecurePin);
		if (!authentication) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Authentication failed. ");
			errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
		}

		accountService.changeStatus(changeStatusAccountId, changeStatus);

		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setMessage("Status of " + changeStatusAccountId + " successfuly updated.");

		return new ResponseEntity<>(successResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * API to retrieve count details.
	 *
	 * @PathVariable country input request
	 * 
	 * @return studentDetailsResponse
	 * 
	 */
	@GetMapping(AccountConstant.RETRIEVE_COUNT)
	public ResponseEntity<Object> retrievePlaceCounts(@PathVariable("country") String country)
			throws AccountServiceException, Exception {

		PlaceCountResponse placeCountResponse;
		placeCountResponse = accountService.retrievePlaceCounts(country);

		return new ResponseEntity<>(placeCountResponse, HttpStatus.OK);
	}

}
