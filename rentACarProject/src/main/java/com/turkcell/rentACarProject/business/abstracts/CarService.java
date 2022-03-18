package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.dtos.get.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface CarService {

	DataResult<List<ListCarDto>> getAll();

	DataResult<GetCarDto> getById(int id) ;

	DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize);

	DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction);

	DataResult<List<ListCarDto>> getAllByDailyPriceLessThanEqual(double dailyPrice);

	Result add(CreateCarRequest createCarRequest) ;

	Result delete(DeleteCarRequest deleteCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	void setOperationForKilometer(int carId, double kilometer);

}
