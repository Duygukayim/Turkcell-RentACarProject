package com.turkcell.rentACarProject.business.requests.additionalService;

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
public class CreateAdditionalServiceRequest {

	@NotNull
	@Size(min = 2, max = 64)
	private String name;
	
	@NotNull
	@Positive
    private double dailyPrice;
	
}
