package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRentalRequest {
	
	private int id;

    private LocalDate returnDate;

    private int carId;
    
    private List<ListOrderedAdditionalServiceDto> orderedAdditionalServiceIds;

	private int RentCityId; 
	
	private int ReturnCityId; 

}
