package com.turkcell.rentACarProject.business.requests.additionalService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {

	@NotNull
	@Positive
	private int id;

	@NotNull
	@Size(min = 2, max = 50)
	private String name;
	
	@NotNull
    private double dailyPrice;
	
}
