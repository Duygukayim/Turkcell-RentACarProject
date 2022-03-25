package com.turkcell.rentACarProject.business.dtos.get;

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
public class GetOrderedAdditionalServiceDto {
	
	private String additionalServiceName;
	
    private int quantity;
    
    private long carRentalId;

}
