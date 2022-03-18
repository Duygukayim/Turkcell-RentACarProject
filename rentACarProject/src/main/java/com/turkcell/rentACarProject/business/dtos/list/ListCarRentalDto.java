package com.turkcell.rentACarProject.business.dtos.list;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarRentalDto {

	private int id;
	
	private LocalDate rentDate;
	
    private LocalDate returnDate;
    
    private double startingKilometer;

    private double returnKilometer;
    
    private int customerId;
    
    private int carId;

	private int RentCityId; 

	private int ReturnCityId; 
    
}
