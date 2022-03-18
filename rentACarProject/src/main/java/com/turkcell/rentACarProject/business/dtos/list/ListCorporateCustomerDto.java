package com.turkcell.rentACarProject.business.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCorporateCustomerDto {
	
	private int id;
	
	private String email;
	
	private String companyName;
	
	private String taxNumber;


}
