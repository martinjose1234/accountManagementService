package com.account.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.client.AccountClient;
import com.account.constant.AccountConstant;
import com.account.entity.AccountEntity;
import com.account.exception.AccountServiceException;
import com.account.model.request.AccountRequest;
import com.account.model.request.UpdateAccountRequest;
import com.account.model.response.AccountResponse;
import com.account.model.response.Place;
import com.account.model.response.PlaceCountResponse;
import com.account.model.response.State;
import com.account.repository.AccountRepository;
import com.account.service.AccountService;
import com.account.util.AccountMapperUtil;

@Service
public class AccountServiceImpl implements AccountService {

	Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountMapperUtil accountMapperUtil;

	@Autowired
	AccountClient accountClient;

	/**
	 * 
	 * Authenticate user.
	 *
	 * 
	 * 
	 * @return boolean
	 * 
	 */
	@Override
	public boolean authenticateUser(String loginAccountId, String loginPin) throws AccountServiceException, Exception {

		boolean authStatus = true;

		List<AccountEntity> accounts = accountRepository.findByAccountIdAndSecuritypin(loginAccountId, loginPin);

		if (null == accounts || accounts.isEmpty()) {
			authStatus = false;
		}

		return authStatus;
	}

	/**
	 * 
	 * Service to add customer account.
	 *
	 * 
	 * 
	 * @return AccountResponse
	 * 
	 */
	@Override
	public AccountResponse addAccount(AccountRequest accountRequest) throws AccountServiceException, Exception {

		logger.info("In AccountService to add an account.");

		// Existing emailId validation
		existingEmailValidation(accountRequest);

		// Third party client call to get place details.
		accountClient.postalServiceCall(accountRequest);

		AccountEntity accountEntity = accountMapperUtil.generateEntityFromRequest(accountRequest);

		AccountEntity accountEntityResponse = accountRepository.save(accountEntity);

		AccountResponse accountResponse = accountMapperUtil.generateResponseFromEntity(accountEntityResponse);

		return accountResponse;
	}

	private void existingEmailValidation(AccountRequest accountRequest) {
		List<AccountEntity> allAccounts = accountRepository.findAll();
		List<AccountEntity> exists = allAccounts.stream().filter(c -> c.getEmail().equals(accountRequest.getEmail()))
				.collect(Collectors.toList());

		if (null != exists && !exists.isEmpty()) {
			throw new AccountServiceException(AccountConstant.EMAIL_ID_ALREADY_EXIST);
		}
	}

	/**
	 * 
	 * Service to update customer account.
	 *
	 * 
	 * 
	 * @return AccountResponse
	 * 
	 */
	@Override
	public AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest) throws AccountServiceException, Exception {

		logger.info("In AccountService to update an account.");

		List<AccountEntity> accounts = accountRepository.findByAccountId(updateAccountRequest.getAccountId());

		if (null == accounts || accounts.isEmpty()) {
			throw new AccountServiceException(AccountConstant.ACCOUNT_NOT_EXIST);
		}
		if (null != accounts.get(0) && !accounts.get(0).getStatus().equals("Active")) {
			throw new AccountServiceException(AccountConstant.ACOUNT_NOTIN_ACTIVE);
		}

		AccountEntity accountEntity = accounts.get(0);

		generateUpdateEntity(updateAccountRequest, accountEntity);

		AccountEntity accountEntityResponse = accountRepository.save(accountEntity);

		AccountResponse accountResponse = accountMapperUtil.generateResponseFromEntity(accountEntityResponse);

		return accountResponse;
	}

	private void generateUpdateEntity(UpdateAccountRequest updateAccountRequest, AccountEntity accountEntity)
			throws Exception {
		if (null != updateAccountRequest.getAge() && updateAccountRequest.getAge() != 0) {
			accountEntity.setAge(updateAccountRequest.getAge());
		}
		if (null != updateAccountRequest.getCountry() && !updateAccountRequest.getCountry().isEmpty()) {

			if (!updateAccountRequest.getCountry().matches(AccountConstant.COUNTRY_REGEX_EXPRESSION)) {
				throw new AccountServiceException("Not valid country.");
			}

			if (null == updateAccountRequest.getPostalcode() || updateAccountRequest.getPostalcode().isEmpty()) {
				throw new AccountServiceException("Please give postal code also.");
			}

			postalServiceForUpdate(updateAccountRequest, accountEntity);
		}
		if (null != updateAccountRequest.getEmail() && !updateAccountRequest.getEmail().isEmpty()) {
			if (!updateAccountRequest.getEmail().matches(AccountConstant.EMAIL_REGEX_EXPRESSION)) {
				throw new AccountServiceException("Not valid email id.");
			}
			accountEntity.setEmail(updateAccountRequest.getEmail());
		}
		if (null != updateAccountRequest.getName() && !updateAccountRequest.getName().isEmpty()) {
			accountEntity.setName(updateAccountRequest.getName());
		}
		if (null != updateAccountRequest.getStatus() && !updateAccountRequest.getStatus().isEmpty()) {
			if (!updateAccountRequest.getStatus().matches(AccountConstant.STATUS_REGEX_EXPRESSION)) {
				throw new AccountServiceException("Not valid Status.");
			}
			accountEntity.setStatus(updateAccountRequest.getStatus());
		}
		if (null != updateAccountRequest.getPostalcode() && !updateAccountRequest.getPostalcode().isEmpty()) {

			if (null == updateAccountRequest.getCountry() || updateAccountRequest.getCountry().isEmpty()) {
				throw new AccountServiceException("Please give country code also.");
			}

			postalServiceForUpdate(updateAccountRequest, accountEntity);

		}
	}

	private void postalServiceForUpdate(UpdateAccountRequest updateAccountRequest, AccountEntity accountEntity)
			throws Exception {
		AccountRequest accountRequest = new AccountRequest();
		accountRequest.setCountry(updateAccountRequest.getCountry());
		accountRequest.setPostalcode(updateAccountRequest.getPostalcode());
		accountClient.postalServiceCall(accountRequest);
		accountEntity.setLatitude(accountRequest.getLatitude());
		accountEntity.setLongitude(accountRequest.getLongitude());
		accountEntity.setPlace(accountRequest.getPlace());
		accountEntity.setState(accountRequest.getState());
		accountEntity.setPostalcode(updateAccountRequest.getPostalcode());
	}

	/**
	 * 
	 * Service to delete account.
	 *
	 * 
	 */
	@Override
	public void deleteAccount(String deleteAccountId) throws AccountServiceException, Exception {

		logger.info("In AccountService to delete an account.");

		List<AccountEntity> accounts = accountRepository.findByAccountId(deleteAccountId);

		if (null == accounts || accounts.isEmpty()) {
			throw new AccountServiceException(AccountConstant.ACCOUNT_NOT_EXIST);
		}
		if (null != accounts.get(0) && !accounts.get(0).getStatus().equals("Inactive")) {
			throw new AccountServiceException("Given account is not in Inactive state.");
		}

		accountRepository.delete(accounts.get(0));
	}

	/**
	 * 
	 * Service to change account status.
	 *
	 * 
	 */
	@Override
	public void changeStatus(String changeStatusAccountId, String changeStatus) throws AccountServiceException, Exception {

		logger.info("In AccountService to change account status.");

		List<AccountEntity> accounts = accountRepository.findByAccountId(changeStatusAccountId);

		if (null == accounts || accounts.isEmpty()) {
			throw new AccountServiceException(AccountConstant.ACCOUNT_NOT_EXIST);
		}
		if (!changeStatus.matches(AccountConstant.STATUS_REGEX_EXPRESSION)) {
			throw new AccountServiceException("Not valid Status.");
		}

		accounts.get(0).setStatus(changeStatus);

		accountRepository.save(accounts.get(0));
	}

	/**
	 * 
	 * Service to retrieve places counts.
	 *
	 * 
	 * 
	 * @return PlaceCountResponse
	 * 
	 */
	@Override
	public PlaceCountResponse retrievePlaceCounts(String country) throws Exception {

		List<AccountEntity> accounts = null;
		PlaceCountResponse placeCountResponse = new PlaceCountResponse();
		placeCountResponse.setCountry(country);

		accounts = accountRepository.findByCountryEqualsOrderByStateAsc(country);

		String previousState = "";
		State state = null;

		String places = "";

		for (AccountEntity account : accounts) {

			if (!previousState.equals(account.getState())) {

				places = "";
				previousState = account.getState();

				// State.
				state = new State();
				state.setCount(1);
				state.setState(account.getState());

				// Place
				Place place = new Place();
				place.setPlace(account.getPlace());
				state.getPlaces().add(place);
				place.setCount(1);
				places = places + account.getPlace();

				placeCountResponse.getStates().add(state);

			} else {

				// State.
				state.setCount(state.getCount() + 1);

				// Place
				if (places.contains(account.getPlace())) {

					for (Place p : state.getPlaces()) {
						if (p.getPlace().equals(account.getPlace())) {
							p.setCount(p.getCount() + 1);
						}
					}

				} else {
					places = places + account.getPlace();
					Place place = new Place();
					place.setCount(1);
					place.setPlace(account.getPlace());
					state.getPlaces().add(place);
				}

			}

			placeCountResponse.setCount(placeCountResponse.getCount() + 1);
		}

		return placeCountResponse;
	}

}
