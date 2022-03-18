package com.turkcell.rentACarProject.business.requests.invoice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	
	@NotNull
	@Positive
	private int carRentalId;
	
	@NotNull
	@Positive
	private int customerId;

}