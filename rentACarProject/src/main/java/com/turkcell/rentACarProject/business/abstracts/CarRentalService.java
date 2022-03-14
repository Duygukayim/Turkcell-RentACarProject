package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarRentalDto;
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.DeleteCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;


public interface CarRentalService {
	
	DataResult<List<ListCarRentalDto>> getAll();

	DataResult<GetCarRentalDto> getById(int id) throws BusinessException;
	
	DataResult<List<GetCarRentalDto>> getByCarId(int id);

	Result add(CreateCarRentalRequest createCarRentalRequest) throws BusinessException;

	Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException;

	Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException;

}
