package com.turkcell.rentACarProject.business.requests.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class UpdateCustomerRequest {

	@NotNull
	@Positive
	private int id;
	
	@NotNull
	@Size(min = 2, max = 50)
    private String firstName;

	@NotNull
	@Size(min = 2, max = 50)
    private String lastName;
	
}
