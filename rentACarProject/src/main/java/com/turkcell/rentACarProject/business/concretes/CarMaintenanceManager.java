package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.constants.CarStatus;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private final CarService carService;
	private final CarMaintenanceDao carMaintenanceDao;
	private final ModelMapperService modelMapperService;
	
	@Autowired
	public CarMaintenanceManager(CarService carService, CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService) {

		this.carService = carService;
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
	}
	

	@Override
	public DataResult<List<GetCarMaintenanceDto>> getAll() {
		
		List<CarMaintenance> result = carMaintenanceDao.findAll();
		List<GetCarMaintenanceDto> response = result.stream().map(carMaintenance -> modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, Messages.CARMAINTENANCELIST);
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(long id) {
		
		checkCarMaintenanceIdExists(id);
		
		CarMaintenance carMaintenance = carMaintenanceDao.getById(id);
		GetCarMaintenanceDto response = modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);
		
		return new SuccessDataResult<>(response, Messages.CARMAINTENANCEFOUND);
	}


	@Override
	public DataResult<List<GetCarMaintenanceDto>> getByCarId(long id) {

		checkIfCarIdExists(id);

		List<CarMaintenance> result = this.carMaintenanceDao.findByCar_Id(id);
		List<GetCarMaintenanceDto> response = result.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, Messages.CARFOUND);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
		
		checkIfCarIdExists(createCarMaintenanceRequest.getCarId());
		checkCarStatus(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
		
		this.carMaintenanceDao.save(carMaintenance);
		carService.setCarStatus(CarStatus.UNDER_MAINTENANCE, createCarMaintenanceRequest.getCarId());
		
		return new SuccessResult(Messages.CARMAINTENANCEADD);
	}

	@Override
	public Result delete(long id) {
		
		checkCarMaintenanceIdExists(id);
		
		this.carMaintenanceDao.deleteById(id);
		carService.setCarStatus(CarStatus.AVAILABLE, carMaintenanceDao.findById(id).getCar().getId());
		
		return new SuccessResult(Messages.CARMAINTENANCEDELETE);
	}
		

	@Override
	public Result update(long id, UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		
		checkCarMaintenanceIdExists(id);
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
        carMaintenance.setId(id);
        
		this.carMaintenanceDao.save(carMaintenance);
		carService.setCarStatus(CarStatus.AVAILABLE, updateCarMaintenanceRequest.getCarId());
		
		return new SuccessResult(Messages.CARMAINTENANCEUPDATE);
	}

	private void checkCarMaintenanceIdExists(long carMaintenanceId) {
		
		if(!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			throw new BusinessException(Messages.CARMAINTENANCENOTFOUND);
		}
	}

	private void checkIfCarIdExists(long carId){
		
		if(this.carService.getById(carId).getData() == null) {
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}
	
	 private void checkCarStatus(long carId) {
	        if (this.carService.getById(carId).getData().getStatus() == CarStatus.RENTED)
	            throw new BusinessException(Messages.CARMAINTENANCERENTALERROR);

	        else if (this.carService.getById(carId).getData().getStatus() == CarStatus.UNDER_MAINTENANCE)
	            throw new BusinessException(Messages.CARISUNDERMAINTENANCE);
	    }
	
}
