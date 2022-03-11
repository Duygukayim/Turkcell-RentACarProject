package com.turkcell.rentACarProject.business.requests.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DeleteCustomerRequest {

	@NotNull
	@Positive
	private int id;
	
}
