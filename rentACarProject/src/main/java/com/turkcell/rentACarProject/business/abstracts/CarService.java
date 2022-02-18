package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.ListCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;

public interface CarService {

	List<ListCarDto> getAll();
	GetCarDto getById(int id);
	
	void create(CreateCarRequest createCarRequest);
	void delete(DeleteCarRequest deleteCarRequest);
	void update(UpdateCarRequest updateCarRequest);


}
