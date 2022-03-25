package com.turkcell.rentACarProject.business.requests.carMaintenance;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
public class UpdateCarMaintenanceRequest {

	@NotNull
	@Size(min = 2, max = 64)
	private String description;

	@FutureOrPresent
	private LocalDate returnDate;

	@NotNull
	@Positive
	private long carId;
}
