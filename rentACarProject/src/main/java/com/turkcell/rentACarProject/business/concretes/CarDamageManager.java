package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarDamageService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarDamageDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarDamageDto;
import com.turkcell.rentACarProject.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACarProject.business.requests.carDamage.DeleteCarDamageRequest;
import com.turkcell.rentACarProject.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {
	
	private CarDao carDao;
	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarDamageManager(CarDao carDao, CarDamageDao carDamageDao, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCarDamageDto>> getAll() {
		
		List<CarDamage> result = this.carDamageDao.findAll();
		List<ListCarDamageDto> response = result.stream().map(carDamage -> this.modelMapperService.forDto().map(carDamage, ListCarDamageDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarDamageDto>>(response, "CarDamages listed successfully.");
	}

	@Override
	public DataResult<GetCarDamageDto> getById(int id) {
		
		CarDamage carDamage = carDamageDao.getById(id);
		checkIfCarDamageIdExists(carDamage.getId());
		GetCarDamageDto response = modelMapperService.forDto().map(carDamage, GetCarDamageDto.class);
		
		return new SuccessDataResult<GetCarDamageDto>(response, "Success");
	}

	@Override
	public DataResult<List<ListCarDamageDto>> getByCarId(int id) {
			
		Car car = this.carDao.getById(id);
		List<CarDamage> result = this.carDamageDao.getByCarId(car.getId());
		List<ListCarDamageDto> response = result.stream().map(carDamage -> this.modelMapperService.forDto().map(carDamage, ListCarDamageDto.class)).collect(Collectors.toList());
			
		return new SuccessDataResult<List<ListCarDamageDto>>(response, "Success");
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		
		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		checkIfCarIdExists(carDamage.getCar().getId());
		this.carDamageDao.save(carDamage);

		return new SuccessResult("CarDamage added successfully.");
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {

		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		checkIfCarDamageIdExists(carDamage.getId());
		checkIfCarIdExists(carDamage.getCar().getId());
		this.carDamageDao.save(carDamage);

		return new SuccessResult("CarDamage updated successfully.");
	}

	@Override
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) {

		CarDamage carDamage = this.modelMapperService.forRequest().map(deleteCarDamageRequest, CarDamage.class);
		checkIfCarDamageIdExists(carDamage.getId());
		this.carDamageDao.delete(carDamage);

		return new SuccessResult("CarDamage deleted successfully.");
	}
	
	private void checkIfCarDamageIdExists(int carDamageId) throws BusinessException {
		
		if(!this.carDamageDao.existsById(carDamageId)) {
			throw new BusinessException("A Car Damage with this ID was not found!");
		}
	}
	
	private void checkIfCarIdExists(int carId) {
		if(!this.carDao.existsById(carId)) {
			throw new BusinessException("A car with this ID was not found!");
		}
	}
	
	
	

}
