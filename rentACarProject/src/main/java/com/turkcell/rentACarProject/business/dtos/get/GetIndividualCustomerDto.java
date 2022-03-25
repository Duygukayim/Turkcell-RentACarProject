package com.turkcell.rentACarProject.business.dtos.get;

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
public class GetIndividualCustomerDto {
	
	private String email;

	private String firstName;

	private String lastName;

	private String identityNumber;

}
