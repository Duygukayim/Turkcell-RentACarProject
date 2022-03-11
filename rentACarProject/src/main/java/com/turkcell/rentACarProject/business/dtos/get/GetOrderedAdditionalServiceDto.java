package com.turkcell.rentACarProject.business.dtos.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderedAdditionalServiceDto {

	private int id;
	private int carRentalId;
	private int additionalServiceId;
    private int customerId;

}
