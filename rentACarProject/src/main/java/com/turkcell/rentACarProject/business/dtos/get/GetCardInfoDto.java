package com.turkcell.rentACarProject.business.dtos.get;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCardInfoDto {
	
	private int id;
	
	private String cardHolderName;

	private String cardNumber;

	private String cardCvvNumber;

	private LocalDate expiryDate;

}
