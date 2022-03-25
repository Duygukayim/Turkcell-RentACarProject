package com.turkcell.rentACarProject.business.dtos.get;

import com.turkcell.rentACarProject.business.constants.CarStatus;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCarDto {
	
	private CarStatus status;
	
	private double dailyPrice;
	
	private int modelYear;
	
	private double mileage;
	
	private String description;
	
	private String brandName;
	
	private String colorName;

}
