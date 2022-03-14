package com.turkcell.rentACarProject.business.dtos.get;

import com.turkcell.rentACarProject.entities.concretes.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderedAdditionalServiceDto {
	
	private int additionalServiceId;
	
    private int quantity;

}
