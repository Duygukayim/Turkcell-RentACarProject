package com.turkcell.rentACarProject.business.requests.orderedAdditionalService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
public class UpdateOrderedAdditionalServiceRequest {

	@NotNull
	@Positive
    private long additionalServiceId;
	
    @Min(1)
    @Positive
    private int quantity;
    
    @NotNull
    @Positive
    private long carRentalId;
	
}
