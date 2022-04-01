package com.turkcell.rentACarProject.business.requests.carRental;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.lang.Nullable;

import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRentalRequest {

	@NotNull
	@Positive
	private long carId;
	
	@FutureOrPresent
    private LocalDate rentDate;
	
	@Future
    private LocalDate returnDate;

	@NotNull
	@Positive
    private long customerId;

	@Nullable
	private Set<CreateOrderedAdditionalServiceRequest> createdOrderedAdditionalServiceRequestSet;
	
	@NotNull
	@Positive
	private long RentCityId; 
	
	@NotNull
	@Positive
	private long ReturnCityId; 
	
	
	
}
