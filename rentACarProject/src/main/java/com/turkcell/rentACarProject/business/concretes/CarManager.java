package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.ListCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.entities.concretes.Car;

@Service
public class CarManager implements CarService {
	
	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<ListCarDto> getAll() {
		var result = this.carDao.findAll();
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void create(CreateCarRequest createCarRequest) {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
	}

	@Override
	public void delete(DeleteCarRequest deleteCarRequest) {
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		this.carDao.delete(car);
		
	}

	@Override
	public void update(UpdateCarRequest updateCarRequest) {
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
		
	}

	@Override
	public GetCarDto getById(int id) {
		Car result = this.carDao.getCarById(id);
		GetCarDto response = this.modelMapperService.forDto().map(result, GetCarDto.class);
		return response;
	}

}
