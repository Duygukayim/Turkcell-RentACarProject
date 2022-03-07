package com.turkcell.rentACarProject.business.requests.carMaintenance;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {

//	@NotNull
	private int id;
	
//	@NotNull
	@Size(min=2, max=100)
	private String description;
	
	private LocalDate returnDate;
	
//	@NotNull
	private int carId;
}
