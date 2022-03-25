package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCarDamageDto;
import com.turkcell.rentACarProject.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACarProject.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface CarDamageService {

	DataResult<List<GetCarDamageDto>> getAll();

	DataResult<GetCarDamageDto> getById(long id);

	DataResult<List<GetCarDamageDto>> getByCarId(long carId);

	Result add(CreateCarDamageRequest createRequest);

	Result update(long id, UpdateCarDamageRequest updateRequest);

	Result delete(long id);


}
