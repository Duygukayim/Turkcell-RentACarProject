package com.turkcell.rentACarProject.business.dtos.get;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCarRentalDto {
	
	private LocalDate rentDate;
	
    private LocalDate returnDate;
    
    private double startingMileage;

    private double returnMileage;
    
    private long customerId;
    
    private long carId;
    
	private String RentCityName; 

	private String ReturnCityName; 
    
    
}
