package com.turkcell.rentACarProject.business.requests.carDamage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDamageRequest {

	@NotNull
	@Size(min=2,max=100)
	private String description;
	
	@NotNull
	private int carId;
}
