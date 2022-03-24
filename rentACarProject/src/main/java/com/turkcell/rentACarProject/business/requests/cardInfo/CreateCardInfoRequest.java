package com.turkcell.rentACarProject.business.requests.cardInfo;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardInfoRequest {

	@Size(min = 2, max = 50)
	private String cardHolderName;

	@Size(min = 2, max = 12)
	private String cardNumber;

	@NotNull
	@Max(3)
	private String cardCvvNumber;

	@Future
	private LocalDate expiryDate;

}