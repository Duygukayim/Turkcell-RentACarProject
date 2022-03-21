package com.turkcell.rentACarProject.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarRentalDto;
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.DeleteCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CityDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;
import com.turkcell.rentACarProject.entities.concretes.CarRental;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;

@Service
public class CarRentalManager implements CarRentalService {

	private CarDao carDao;
	private CityDao cityDao;
	private CarRentalDao carRentalDao;
	private CarMaintenanceDao carMaintenanceDao;
	private AdditionalServiceDao additionalServiceDao;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarRentalManager(CarDao carDao, CityDao cityDao, CarRentalDao carRentalDao, CarMaintenanceDao carMaintenanceDao,
			AdditionalServiceDao additionalServiceDao, OrderedAdditionalServiceService orderedAdditionalServiceService, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.cityDao = cityDao;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceDao = carMaintenanceDao;
		this.additionalServiceDao = additionalServiceDao;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCarRentalDto>> getAll() {
		
		List<CarRental> result = carRentalDao.findAll();
		List<ListCarRentalDto> response = result.stream().map(carRental -> modelMapperService.forDto().map(carRental, ListCarRentalDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarRentalDto>>(response, "Success");
	}

	@Override
	public DataResult<GetCarRentalDto> getById(int id) throws BusinessException {
		
		CarRental carRental = carRentalDao.getById(id);
		checkCarRentalIdExists(carRental.getId());
		GetCarRentalDto response = modelMapperService.forDto().map(carRental, GetCarRentalDto.class);
		
		return new SuccessDataResult<GetCarRentalDto>(response, "Success");
	}
	
	@Override
	public DataResult<List<GetCarRentalDto>> getByCarId(int id) {
		
		Car car = this.carDao.getById(id);
		List<CarRental> result = this.carRentalDao.getCarRentalsByCarId(car.getId());
		List<GetCarRentalDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, GetCarRentalDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarRentalDto>>(response , "Success");
	}

	@Override
	public Result createForCorporateCustomer(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {
		
		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		checkIfCarIdExists(carRental.getCar().getId());
		checkIfCityIdExists(carRental.getRentCity().getId());
		checkIfCityIdExists(carRental.getReturnCity().getId());
		checkUnderMaintenance(carRental);
		this.carRentalDao.save(carRental);

		return new SuccessResult("CarRental.Added");
	}
	
	@Override
	public Result createForIndividualCustomer(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {
		
		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		checkIfCarIdExists(carRental.getCar().getId());
		checkIfCityIdExists(carRental.getRentCity().getId());
		checkIfCityIdExists(carRental.getReturnCity().getId());
		checkUnderMaintenance(carRental);
		this.carRentalDao.save(carRental);

		return new SuccessResult("CarRental.Added");
	}

	@Override
	public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) {
		
		CarRental carRental = this.modelMapperService.forRequest().map(deleteCarRentalRequest, CarRental.class);
		checkCarRentalIdExists(carRental.getId());
		this.carRentalDao.deleteById(carRental.getId());
		
		return new SuccessResult("CarRental.Deleted");
	}

	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) {
		
		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest, CarRental.class);
		checkCarRentalIdExists(carRental.getId());
		checkUnderMaintenance(carRental);
		updateOperation(carRental, updateCarRentalRequest);
		carRental.getCar().setKilometerInfo(updateCarRentalRequest.getReturnKilometer());
		this.carRentalDao.save(carRental);
		
		return new SuccessResult("CarRental.Updated");
	}

	private void checkCarRentalIdExists(int carRentalId) {
		if(!this.carRentalDao.existsById(carRentalId)) {
			throw new BusinessException("Car Rental with this ID was not found!");
		}
	}

	private void checkIfCarIdExists(int carId) {
		if(!this.carDao.existsById(carId)) {
			throw new BusinessException("A car with this ID was not found!");
		}
	}
	
	private void checkIfCityIdExists(int cityId) {
		
		if(!this.cityDao.existsById(cityId)) {
			throw new BusinessException("A city with this ID was not found!");
		}
	}
	

	private void checkUnderMaintenance(CarRental carRental) {
		
		List<CarMaintenance> result = this.carMaintenanceDao.getCarMaintenanceByCarId(carRental.getCar().getId());
		if (result != null) {
			for (CarMaintenance carMaintenance : result) {
				if (carMaintenance.getReturnDate() != null) {
					throw new BusinessException("Car is still under maintance!");
				}
			}
		}
	}
	
	private void updateOperation(CarRental carRental, UpdateCarRentalRequest updateCarRentalRequest) {
		
		carRental.setReturnDate(updateCarRentalRequest.getReturnDate());
		carRental.setReturnKilometer(updateCarRentalRequest.getReturnKilometer());	
	}

	@Override
	public DataResult<CarRental> getCarRentalByRentalId(int id) {
		
		return new SuccessDataResult<CarRental>(this.carRentalDao.getById(id));
	}

	@Override
	public double calculateRentalTotalPrice(int carRentalId) {
		
		double totalPrice = 0;
		
		CarRental carRental = this.carRentalDao.getById(carRentalId);
		totalPrice = carRental.getDailyPrice();
		
		for (OrderedAdditionalService carRentedService : carRental.getOrderedAdditionalServices()) {
			totalPrice += carRentedService.getAdditionalService().getDailyPrice() * carRentedService.getQuantity();
		}
		
		long days = ChronoUnit.DAYS.between(carRental.getRentDate(), carRental.getReturnDate());
		
		if(days == 0) {
			days = 1;
		
			totalPrice = days * totalPrice;
		}
		
		if (carRental.getRentCity().getId() != carRental.getReturnCity().getId()) {
				totalPrice += 750;
		}
		return totalPrice;
	}


}
