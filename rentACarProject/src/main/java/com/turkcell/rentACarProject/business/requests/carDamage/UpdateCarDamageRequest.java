package com.turkcell.rentACarProject.business.requests.carDamage;

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
public class UpdateCarDamageRequest {

	@NotNull
	@Size(min = 2, max = 64)
	private String description;

	@NotNull
	@Positive
	private long carId;
	
}
