package com.turkcell.rentACarProject.business.requests.corporateCustomer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCorporateCustomerRequest {
	
	@NotNull
    @Positive
    private int customerId;

}
