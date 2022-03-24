package com.turkcell.rentACarProject.business.requests.payment;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

	@NotNull
	private int CarRentalId;
	
	private double totalPayment;
	
	@NotNull
	private String cardHolderName;  
	
	@NotNull
	private String cardNumber;
	
	@NotNull
	private String cardCvvNumber;
	
	@Future
	private LocalDate expiryDate;
	
}
