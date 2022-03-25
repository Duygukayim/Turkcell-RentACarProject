package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
public class UpdateCarRentalRequest {
	
	@NotNull
	@Positive
	private long carId;
	
	@NotNull
	@Positive
    private double returnMileage;
	
	@FutureOrPresent
    private LocalDate rentDate;
	
	@Future
    private LocalDate returnDate;

	@NotNull
	@Positive
    private long customerId;

	@NotNull
	@Positive
	private long RentCityId; 
	
	@NotNull
	@Positive
	private long ReturnCityId; 

}
