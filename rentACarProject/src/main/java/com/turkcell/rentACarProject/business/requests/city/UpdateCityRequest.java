package com.turkcell.rentACarProject.business.requests.city;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {
	
	@NotNull
	@Positive
	private int id;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;

}
