package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.dtos.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.ListCarRentalDto;
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.DeleteCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface CarRentalService {
	
	DataResult<List<ListCarRentalDto>> getAll();

	DataResult<GetCarRentalDto> getById(int id);

//	DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize);
//
//	DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction);

	Result add(CreateCarRentalRequest createCarRentalRequest) throws BusinessException;

	Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException;

	Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException;

}
