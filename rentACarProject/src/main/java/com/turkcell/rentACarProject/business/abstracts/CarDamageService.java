package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCarDamageDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarDamageDto;
import com.turkcell.rentACarProject.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACarProject.business.requests.carDamage.DeleteCarDamageRequest;
import com.turkcell.rentACarProject.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface CarDamageService {

	DataResult<List<ListCarDamageDto>> getAll();

	DataResult<GetCarDamageDto> getById(int id);

	DataResult<List<ListCarDamageDto>> getByCarId(int carId);

	Result add(CreateCarDamageRequest createCarDamageRequest);

	Result update(UpdateCarDamageRequest updateCarDamageRequest);

	Result delete(DeleteCarDamageRequest deleteCarDamageRequest);

}
