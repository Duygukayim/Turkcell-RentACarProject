package com.turkcell.rentACarProject.business.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListIndividualCustomerDto {
	
	private int id;
	
	private String email;

	private String firstName;

	private String lastName;

	private String identityNumber;

}
