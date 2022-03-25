package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.constants.CarStatus;
import com.turkcell.rentACarProject.business.dtos.get.GetCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface CarService {

	DataResult<List<GetCarDto>> getAll();

	DataResult<GetCarDto> getById(long id) ;

	DataResult<List<GetCarDto>> getAllPaged(int pageNumber, int pageSize);

	DataResult<List<GetCarDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<GetCarDto>> getAllByDailyPriceLessThanEqual(double dailyPrice);

	Result add(CreateCarRequest createRequest) ;

	Result delete(long id);

	Result update(long id, UpdateCarRequest updateRequest);
	
	void setCarStatus(CarStatus status, long carId);

	void setMileage(double returnMileage, long carId);

}
