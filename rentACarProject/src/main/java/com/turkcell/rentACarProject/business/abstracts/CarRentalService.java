package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;


public interface CarRentalService {
	
	DataResult<List<GetCarRentalDto>> getAll();

	DataResult<GetCarRentalDto> getById(long id);
	
	DataResult<List<GetCarRentalDto>> getByCarId(long carId);

	DataResult<List<GetCarRentalDto>> getByCustomerId(long customerId);
	
	Result addForCorporateCustomer(CreateCarRentalRequest createRequest);

	Result addForIndividualCustomer(CreateCarRentalRequest createRequest);  
	
	Result delete(long id);

	Result update(long id, UpdateCarRentalRequest updateRequest);
	
	double calTotalPrice(long carRentalId);

}
