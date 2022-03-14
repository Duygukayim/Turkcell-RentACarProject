package com.turkcell.rentACarProject.business.requests.individualCustomer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	
	@NotNull
    @Positive
    private int userId;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Size(min=2,max=20)
    private String firstName;

    @NotNull
    @Size(min=2,max=20)
    private String lastName;

    @NotNull
    @Size(min=2,max=20)
    private String nationalIdentity;

}
