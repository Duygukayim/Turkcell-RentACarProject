package com.turkcell.rentACarProject.business.requests.payment;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

	@NotNull
	private int CarRentalId;

	@NotNull
	@Size(min = 2, max = 64)
	private String cardHolderName;

	@NotNull
	@Size(min = 16, max = 16)
	private String cardNumber;

	@NotNull
	@Size(min = 3, max = 3)
	private String cardCvvNumber;

	@Future
	private LocalDate expiryDate;

}
