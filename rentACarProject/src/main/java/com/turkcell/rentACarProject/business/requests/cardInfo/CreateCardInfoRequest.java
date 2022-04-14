package com.turkcell.rentACarProject.business.requests.cardInfo;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class CreateCardInfoRequest {

	@Size(min = 2, max = 64)
	private String cardHolderName;

	@Size(min = 16, max = 16)
	private String cardNumber;

	@NotNull
	@Size(min = 3, max = 3)
	private String cardCvvNumber;

	@Future
	private LocalDate expiryDate;

}
