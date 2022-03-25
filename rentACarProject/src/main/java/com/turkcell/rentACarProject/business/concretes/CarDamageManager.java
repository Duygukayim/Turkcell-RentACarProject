package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarDamageService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.constants.CarStatus;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarDamageDto;
import com.turkcell.rentACarProject.business.requests.carDamage.CreateCarDamageRequest;
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
	private CarService carService;
	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;


	@Autowired
	public CarDamageManager(CarDao carDao, CarService carService, CarDamageDao carDamageDao,
			ModelMapperService modelMapperService) {
		super();
		this.carDao = carDao;
		this.carService = carService;
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
	}


	@Override
	public DataResult<List<GetCarDamageDto>> getAll() {
		
		List<CarDamage> result = this.carDamageDao.findAll();
		List<GetCarDamageDto> response = result.stream().map(carDamage -> this.modelMapperService.forDto().map(carDamage, GetCarDamageDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<GetCarDamageDto>>(response, Messages.DAMAGELIST);
	}

	@Override
	public DataResult<GetCarDamageDto> getById(long id) {
		
		checkIfCarDamageIdExists(id);
		
		CarDamage carDamage = carDamageDao.getById(id);
		GetCarDamageDto response = modelMapperService.forDto().map(carDamage, GetCarDamageDto.class);
		
		return new SuccessDataResult<GetCarDamageDto>(response, Messages.DAMAGEFOUND);
	}

	@Override
	public DataResult<List<GetCarDamageDto>> getByCarId(long id) {
			
		Car car = this.carDao.getById(id);
		List<CarDamage> result = this.carDamageDao.findByCar_Id(car.getId());
		List<GetCarDamageDto> response = result.stream().map(carDamage -> this.modelMapperService.forDto().map(carDamage, GetCarDamageDto.class)).collect(Collectors.toList());
			
		return new SuccessDataResult<List<GetCarDamageDto>>(response, Messages.DAMAGELIST);
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {
		
		checkIfCarIdExists(createCarDamageRequest.getCarId());
		
		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		this.carDamageDao.save(carDamage);
		
		carService.setCarStatus(CarStatus.DAMAGED, createCarDamageRequest.getCarId());

		return new SuccessResult(Messages.DAMAGEADD);
	}

	@Override
	public Result update(long id, UpdateCarDamageRequest updateCarDamageRequest) {
		
		checkIfCarDamageIdExists(id);
		checkIfCarIdExists(updateCarDamageRequest.getCarId());

		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		carDamage.setId(id);
		
		this.carDamageDao.save(carDamage);
		carService.setCarStatus(CarStatus.AVAILABLE, updateCarDamageRequest.getCarId());

		return new SuccessResult(Messages.DAMAGEUPDATE);
	}

	@Override
	public Result delete(long id) {
		
		checkIfCarDamageIdExists(id);
		
		this.carDamageDao.deleteById(id);
		carService.setCarStatus(CarStatus.AVAILABLE, carDamageDao.findById(id).getCar().getId());

		return new SuccessResult(Messages.DAMAGEDELETE);
	}
	
	
	private void checkIfCarDamageIdExists(long carDamageId) {
		
		if(!this.carDamageDao.existsById(carDamageId)) {
			throw new BusinessException(Messages.DAMAGENOTFOUND);
		}
	}
	
	private void checkIfCarIdExists(long carId) {
		if(!this.carDao.existsById(carId)) {
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}
	
}
