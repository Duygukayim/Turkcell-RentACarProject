package com.turkcell.rentACarProject.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarDto {
	
	private int carId;
	private double carDailyPrice;
	private int carModelYear;
	private String carDescription;
	private String brandName;
	private String colorName;

}
