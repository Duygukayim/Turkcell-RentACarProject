package com.turkcell.rentACarProject.business.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarDto {
	
	private int id;
	
	private double dailyPrice;
	
	private int modelYear;
	
	private String description;
	
	private String brandName;
	
	private String colorName;
	
    private int cityId;

}
