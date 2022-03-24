package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRentalRequest {
	
	@NotNull
	@Positive
	private int id;

    @FutureOrPresent
    private LocalDate returnDate;
    
	@NotNull
    private int returnKilometer;

	@NotNull
	@Positive
    private int carId;
	
	@NotNull
	@Positive
	private int ReturnCityId; 

}
