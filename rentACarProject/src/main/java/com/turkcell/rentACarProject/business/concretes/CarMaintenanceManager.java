package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;
import com.turkcell.rentACarProject.entities.concretes.CarRental;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarDao carDao;
	private CarRentalDao carRentalDao;
	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarMaintenanceManager(CarDao carDao, @Lazy CarRentalDao carRentalDao, CarMaintenanceDao carMaintenanceDao,
			ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		
		List<CarMaintenance> result = carMaintenanceDao.findAll();
		List<ListCarMaintenanceDto> response = result.stream().map(carMaintenance -> modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, Messages.CARMAINTENANCELIST);
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int id) throws BusinessException {
		
		CarMaintenance carMaintenance = carMaintenanceDao.getById(id);
		checkCarMaintenanceIdExists(carMaintenance.getId());
		GetCarMaintenanceDto response = modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);
		
		return new SuccessDataResult<GetCarMaintenanceDto>(response, Messages.CARMAINTENANCENOTFOUND);
	}


	@Override
	public DataResult<List<GetCarMaintenanceDto>> getByCarId(int id) {
		
		Car car = this.carDao.getById(id);
		List<CarMaintenance> result = this.carMaintenanceDao.getCarMaintenanceByCarId(car.getId());
		List<GetCarMaintenanceDto> response = result.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarMaintenanceDto>>(response, Messages.CARLIST);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
		
		checkIfCarExists(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setId(0);
		checkIsRented(carMaintenance);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(Messages.CARMAINTENANCEADD);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {
		
		checkCarMaintenanceIdExists(deleteCarMaintenanceRequest.getId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest, CarMaintenance.class);
		this.carMaintenanceDao.deleteById(carMaintenance.getId());
		
		return new SuccessResult(Messages.CARMAINTENANCEDELETE);
	}
		

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest){
		
		checkCarMaintenanceIdExists(updateCarMaintenanceRequest.getId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
		checkIsRented(carMaintenance);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(Messages.CARMAINTENANCEUPDATE);
	}

	private void checkCarMaintenanceIdExists(int carMaintenanceId) {
		
		if(!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			throw new BusinessException(Messages.CARMAINTENANCENOTFOUND);
		}
	}

	private void checkIfCarExists(int carId) throws BusinessException {
		
		if(!this.carDao.existsById(carId)) {
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}

	private void checkIsRented(CarMaintenance carMaintenance) {
		
		List<CarRental> result = this.carRentalDao.getCarRentalsByCarId(carMaintenance.getCar().getId());
		if (result != null) {
			for (CarRental rental : result) {
				if (rental.getReturnDate() != null) {
					throw new BusinessException(Messages.CARRENTED);
				}
			}
		}	
	}
	
}
