package com.turkcell.rentACarProject.business.dtos.get;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarRentalDto {

	private int id;
	
    private LocalDate returnDate;
    
    private int customerId;
    
    private int carId;
    
    private List<ListOrderedAdditionalServiceDto> additionalServiceId;
    
	private int RentCityId; 

	private int ReturnCityId; 
    
    
}
