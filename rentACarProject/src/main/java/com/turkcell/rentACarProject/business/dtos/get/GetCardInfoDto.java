package com.turkcell.rentACarProject.business.dtos.get;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCardInfoDto {

	private String cardHolderName;

	private String cardNumber;

	private String cardCvvNumber;

	private LocalDate expiryDate;

}
