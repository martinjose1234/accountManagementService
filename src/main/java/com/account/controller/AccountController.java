package com.account.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.account.constant.AccountConstant;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;
import com.account.model.response.ErrorResponse;
import com.account.model.response.PlaceCountResponse;
import com.account.model.response.SuccessResponse;
import com.account.service.AccountService;

@RestController
@RequestMapping(path = AccountConstant.BASE_PATH)
public class AccountController {

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
	public ResponseEntity<Object> addAccount(@RequestBody @Valid AccountRequest accountRequest, BindingResult result) {

		// validating request by SpringBoot validator @Valid.
		if (result.hasErrors()) {
			errorResponse = new ErrorResponse();
			errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
			String message = result.getFieldErrors().stream()
					.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
					.collect(Collectors.joining("; "));
			errorResponse.setMessage(message);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}

		try {
			accountResponse = accountService.addAccount(accountRequest);

		} catch (Exception ex) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Exception occured while processing Add Account : " + ex);
			errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(accountResponse, HttpStatus.OK);
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
			BindingResult result, @RequestParam String loginAccountId, @RequestParam String loginSecurePin) {

		// validating request by SpringBoot validator @Valid.
		if (result.hasErrors()) {
			errorResponse = new ErrorResponse();
			errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
			String message = result.getFieldErrors().stream()
					.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
					.collect(Collectors.joining("; "));
			errorResponse.setMessage(message);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}

		try {

			// Authenticate user.
			boolean authentication = accountService.authenticateUser(loginAccountId, loginSecurePin);
			if (!authentication) {
				errorResponse = new ErrorResponse();
				errorResponse.setMessage("Authentication failed. ");
				errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
				return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
			}

			accountResponse = accountService.updateAccount(updateAccountRequest);

		} catch (Exception ex) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Exception occured while processing Update Account : " + ex);
			errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

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
	public ResponseEntity<Object> deleteAccount(@RequestParam String loginAccountId, @RequestParam String loginSecurePin,
			@RequestParam String deleteAccountId) {

		try {

			// Authenticate user.
			boolean authentication = accountService.authenticateUser(loginAccountId, loginSecurePin);
			if (!authentication) {
				errorResponse = new ErrorResponse();
				errorResponse.setMessage("Authentication failed. ");
				errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
				return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
			}

			accountService.deleteAccount(deleteAccountId);

		} catch (Exception ex) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Exception occured while processing Update Account : " + ex);
			errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

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
	 * @param changeStatus      the new status.
	 * 
	 * @return successResponse
	 * 
	 */
	@PutMapping(path = AccountConstant.CHANGE_STATUS, produces = AccountConstant.JSON_CONTENT_TYPE)
	public ResponseEntity<Object> changeStatus(@RequestParam String loginAccountId, @RequestParam String loginSecurePin,
			@RequestParam String changeStatusAccountId, @RequestParam String changeStatus) {

		try {

			// Authenticate user.
			boolean authentication = accountService.authenticateUser(loginAccountId, loginSecurePin);
			if (!authentication) {
				errorResponse = new ErrorResponse();
				errorResponse.setMessage("Authentication failed. ");
				errorResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
				return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
			}

			accountService.changeStatus(changeStatusAccountId, changeStatus);

		} catch (Exception ex) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Exception occured while processing Update Account : " + ex);
			errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

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
	public ResponseEntity<Object> retrievePlaceCounts(@PathVariable("country") String country) {

		PlaceCountResponse placeCountResponse;
		try {
			placeCountResponse = accountService.retrievePlaceCounts(country);
		} catch (Exception ex) {
			errorResponse = new ErrorResponse();
			errorResponse.setMessage("Exception occured while retrieving place counts : " + ex);
			errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(placeCountResponse, HttpStatus.OK);
	}

}
