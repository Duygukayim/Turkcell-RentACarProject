package com.turkcell.rentACarProject.business.requests.orderedAdditionalService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {

	@NotNull
	@Positive
	private int id;
	
	@NotNull
	@Positive
    private int additionalServiceId;
	
    @Min(1)
    @Positive
    private int quantity;
	
}