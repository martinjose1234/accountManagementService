package com.account.model.client;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostalClientServiceResponse {
	
	public String postcode;
	public String country;
	public String countryabbreviation;
	public List<Place> places;

}
