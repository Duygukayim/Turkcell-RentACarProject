package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;

import com.turkcell.rentACarProject.entities.concretes.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRentalRequest {
	
	private int id;

    private LocalDate returnDate;

    private int carId;
    
    private int additionalServiceId;

}
