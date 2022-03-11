package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRentalRequest {

	@NotNull
	@Positive
	private int carId;
	
	@FutureOrPresent
    private LocalDate returnDate;

	@NotNull
	@Positive
    private int customerId;

	@NotNull
	@Positive
    private List<Integer> additionalServiceIds;
}
