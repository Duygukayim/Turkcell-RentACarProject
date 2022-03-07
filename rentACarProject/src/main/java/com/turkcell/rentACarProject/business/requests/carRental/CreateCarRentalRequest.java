package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.turkcell.rentACarProject.entities.concretes.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRentalRequest {
	
	private LocalDate rentDate;

    private LocalDate returnDate;

	@NotNull
    private int customerId;

//	@NotNull
    private int carId;

    private int additionalServiceId;
}
