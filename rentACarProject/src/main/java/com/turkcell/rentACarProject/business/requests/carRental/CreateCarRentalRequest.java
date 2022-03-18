package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.lang.Nullable;

import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRentalRequest {

	@NotNull
	@Positive
	private int carId;
	
    private LocalDate rentDate;
	
	@FutureOrPresent
    private LocalDate returnDate;

	@NotNull
	@Positive
    private int customerId;

	@Nullable
    private List<ListOrderedAdditionalServiceDto> orderedAdditionalServiceIds;
	
	@NotNull
	@Positive
	private int RentCityId; 
	
	@NotNull
	@Positive
	private int ReturnCityId; 
	
}
