package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
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
	public CarMaintenanceManager(CarDao carDao, CarRentalDao carRentalDao, CarMaintenanceDao carMaintenanceDao,
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

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(response, "Success");
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int id) throws BusinessException {
		
		CarMaintenance carMaintenance = carMaintenanceDao.getById(id);
			if (carMaintenance == null) {
				return new ErrorDataResult<GetCarMaintenanceDto>("A color with this ID was not found!");
			}
		checkCarMaintenanceIdExists(carMaintenance.getId());
		GetCarMaintenanceDto response = modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class);
		
		return new SuccessDataResult<GetCarMaintenanceDto>(response, "Success");
	}


	@Override
	public DataResult<List<GetCarMaintenanceDto>> getByCarId(int id) {
		
		Car car = this.carDao.getById(id);
		List<CarMaintenance> result = this.carMaintenanceDao.getCarMaintenanceByCarId(car);
		List<GetCarMaintenanceDto> response = result.stream().map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, GetCarMaintenanceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarMaintenanceDto>>(response, "Success");
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setId(0);
		checkIfCarExists(carMaintenance.getCarId().getId());
		checkIsRented(carMaintenance);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("CarMaintenance.Added");
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest, CarMaintenance.class);
		checkCarMaintenanceIdExists(carMaintenance.getId());
		this.carMaintenanceDao.deleteById(carMaintenance.getId());
		
		return new SuccessResult("CarMaintenance.Deleted");
	}
		

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
		
		checkCarMaintenanceIdExists(carMaintenance.getId());
		checkIsRented(carMaintenance);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("CarMaintenance.Updated");
	}

	private void checkCarMaintenanceIdExists(int carMaintenanceId) throws BusinessException {
		
		if(!this.carMaintenanceDao.existsById(carMaintenanceId)) {
			throw new BusinessException("Car Maintenance with this ID was not found!");
		}
	}

	private void checkIfCarExists(int carId) throws BusinessException {
		
		if(!this.carDao.existsById(carId)) {
			throw new BusinessException("Car with this ID was not found!");
		}
	}

	private void checkIsRented(CarMaintenance carMaintenance) throws BusinessException {
		
		List<CarRental> result = this.carRentalDao.getCarRentalsByCarId(carMaintenance.getCarId());
		if (result != null) {
			for (CarRental rental : result) {
				if (rental.getReturnDate() != null) {
					throw new BusinessException("Car is already rented!");
				}
			}
		}	
	}

}
