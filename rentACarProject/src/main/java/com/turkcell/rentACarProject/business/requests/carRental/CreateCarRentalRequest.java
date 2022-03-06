package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentACarProject.entities.concretes.Car;

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
    @Positive
    private int customerId;

	@NotNull
    @Positive
    private int carId;

}
