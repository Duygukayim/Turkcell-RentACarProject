package com.turkcell.rentACarProject.business.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarDto {

	private int id;
	
	private double dailyPrice;
	
	private int modelYear;
	
	private double kilometerInfo;
	
	private String description;
	
	private String brandName;
	
	private String colorName;

}
