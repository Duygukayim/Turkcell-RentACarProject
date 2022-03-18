package com.turkcell.rentACarProject.business.requests.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {

	@NotNull
	@Positive
	private int id;
	
	@Email
    @NotNull
    private String email;

    @NotNull
    private String password;
	
}
