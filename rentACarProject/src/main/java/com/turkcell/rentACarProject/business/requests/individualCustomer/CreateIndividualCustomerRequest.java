package com.turkcell.rentACarProject.business.requests.individualCustomer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {

	@Email
	@NotNull
	private String email;

	@NotNull
	@Size(min = 2, max = 10)
	private String password;

	@NotNull
	@Size(min = 2, max = 64)
	private String firstName;

	@NotNull
	@Size(min = 2, max = 64)
	private String lastName;

	@NotNull
	@Size(min = 11, max = 11)
	private String identityNumber;

}
