package com.turkcell.rentACarProject.business.dtos.list;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListPaymentDto {
	
	@NotNull
	private int CarRentalId;
	
	private double totalPayment;
	
	@NotNull
	private String cardHolderName;  
	
	@NotNull
	private String cardNumber;
	
	@NotNull
	private String cardCvvNumber;
	
	private LocalDate expiryDate;

}
