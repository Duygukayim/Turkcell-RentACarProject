package com.turkcell.rentACarProject.business.requests.invoice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
public class CreateInvoiceRequest {
	
	@NotNull
	@Positive
	private long paymentId;
	
}
