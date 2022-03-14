package com.turkcell.rentACarProject.business.requests.customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

	@NotNull
	@Size(min = 2, max = 50)
    private String firstName;

	@NotNull
	@Size(min = 2, max = 50)
    private String lastName;
    
}