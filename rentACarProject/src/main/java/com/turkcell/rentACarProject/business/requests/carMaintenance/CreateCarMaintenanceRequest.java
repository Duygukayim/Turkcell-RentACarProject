package com.turkcell.rentACarProject.business.requests.carMaintenance;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

	@NotNull
	@Size(min = 2, max = 100)
	private String description;
	
	@FutureOrPresent
	private LocalDate returnDate;

	@NotNull
	@Positive
	private int carId;

}
