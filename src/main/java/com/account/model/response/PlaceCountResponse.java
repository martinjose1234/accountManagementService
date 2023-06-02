package com.account.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceCountResponse {

	private String country;
	private Integer count = 0;
	private List<State> states = new ArrayList<>();

}
