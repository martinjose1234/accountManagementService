package com.account.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class State {

	private String state;
	private Integer count;
	private List<Place> places = new ArrayList<>();

}
