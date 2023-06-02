package com.account.model.client;

import java.util.ArrayList;

public class PostalClientServiceResponse {
	public String postcode;
	public String country;
	public String countryabbreviation;
	public ArrayList<Place> places;

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryabbreviation() {
		return countryabbreviation;
	}

	public void setCountryabbreviation(String countryabbreviation) {
		this.countryabbreviation = countryabbreviation;
	}

	public ArrayList<Place> getPlaces() {
		return places;
	}

	public void setPlaces(ArrayList<Place> places) {
		this.places = places;
	}

}
