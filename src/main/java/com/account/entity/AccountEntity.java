package com.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
	@Column(name = "accountId", length = 6)
	private String accountId;

	@Column(unique = true)
	private String email;

	@Column(name = "securitypin")
	private String securitypin;

	@Column(name = "name")
	private String name;

	@Column(name = "country")
	private String country;

	@Column(name = "postalcode")
	private String postalcode;

	@Column(name = "age")
	private Integer age;

	@Column(name = "status")
	private String status;

	@Column(name = "place")
	private String place;

	@Column(name = "state")
	private String state;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "latitude")
	private String latitude;

}
