package com.turkcell.rentACarProject.business.requests.corporateCustomer;

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
public class CreateCorporateCustomerRequest {

	@Email
	@NotNull
	private String email;

	@NotNull
	@Size(min = 2, max = 10)
	private String password;

	@NotNull
	@Size(min = 2, max = 64)
	private String companyName;

	@NotNull
	@Size(min = 10, max = 10)
	private String taxNumber;

}
