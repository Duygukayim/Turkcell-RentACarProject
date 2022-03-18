package com.turkcell.rentACarProject.business.requests.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

	@Email
    @NotNull
    private String email;

    @NotNull
    private String password;
    
}
