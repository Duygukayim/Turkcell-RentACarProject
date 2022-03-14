package com.turkcell.rentACarProject.business.concretes;

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
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
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
		result.get(0).getOrderedAdditionalServices();
		System.out.println(result.get(0).getOrderedAdditionalServices());
		
		return new SuccessDataResult<List<ListCarRentalDto>>(response);
	}

	@Override
	public DataResult<GetCarRentalDto> getById(int id) throws BusinessException {
		
		CarRental carRental = carRentalDao.getById(id);
			if(carRental == null) {
				return new ErrorDataResult<GetCarRentalDto>("CarRental.NotFound , Rental with this ID was not found!");
			}
		checkCarRentalIdExists(carRental.getId());
		GetCarRentalDto response = modelMapperService.forDto().map(carRental, GetCarRentalDto.class);
		return new SuccessDataResult<GetCarRentalDto>(response, "Success");
	}
	
	@Override
	public DataResult<List<GetCarRentalDto>> getByCarId(int id) {
		
		Car car = this.carDao.getById(id);
		List<CarRental> result = this.carRentalDao.getCarRentalsByCarId(car);
		List<GetCarRentalDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, GetCarRentalDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarRentalDto>>(response , "Success");
	}

	@Override
	public Result add(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {
		
		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		checkIfCarIdExists(carRental.getCarId().getId());
		checkUnderMaintenance(carRental);
		checkIfCityIdExists(carRental.getRentCity().getId());
		checkIfCityIdExists(carRental.getReturnCity().getId());
		this.orderedAdditionalServiceService.add(createCarRentalRequest.getOrderedAdditionalServiceIds() , carRental.getId());
        carRental.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getByCarRentalId(carRental.getId()));
		this.carRentalDao.save(carRental);
		
		return new SuccessResult("CarRental.Added");
	}

	@Override
	public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException {
		
		CarRental carRental = this.modelMapperService.forRequest().map(deleteCarRentalRequest, CarRental.class);
		checkCarRentalIdExists(carRental.getId());
		this.carRentalDao.deleteById(carRental.getId());
		
		return new SuccessResult("CarRental.Deleted");
	}

	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException {
		
		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest, CarRental.class);
		checkCarRentalIdExists(carRental.getId());
		checkUnderMaintenance(carRental);
		this.carRentalDao.save(carRental);
		
		return new SuccessResult("CarRental.Updated");
	}

	private void checkCarRentalIdExists(int carRentalId) throws BusinessException {
		if(!this.carRentalDao.existsById(carRentalId)) {
			throw new BusinessException("Car Rental with this ID was not found!");
		}
	}

	private void checkIfCarIdExists(int carId) throws BusinessException {
		if(!this.carDao.existsById(carId)) {
			throw new BusinessException("A car with this ID was not found!");
		}
	}
	
	private void checkIfAdditionalServiceIdExists(int additionalServiceId) throws BusinessException {
		if(!this.additionalServiceDao.existsById(additionalServiceId)) {
			throw new BusinessException("Additional Service with this ID was not found!");
		}
	}
	
	
	private void checkIfCityIdExists(int cityId) throws BusinessException {
		
		if(!this.cityDao.existsById(cityId)) {
			throw new BusinessException("A city with this ID was not found!");
		}
	}
	
	private void calculateTotalDailyPrice(int carRentalId) {
		
		CarRental carRental = this.carRentalDao.getById(carRentalId);
		double totalPrice = carRental.getDailyPrice();
		
		for (OrderedAdditionalService carRentedService : carRental.getOrderedAdditionalServices()) {
			totalPrice += carRentedService.getAdditionalService().getDailyPrice();
		}
		if (carRental.getRentCity() != carRental.getReturnCity()) {
			totalPrice += 750;
		}
		carRental.setDailyPrice(totalPrice);
	}

	private void checkUnderMaintenance(CarRental carRental) throws BusinessException {
		List<CarMaintenance> result = this.carMaintenanceDao.getCarMaintenanceByCarId(carRental.getCarId());
		if (result != null) {
			for (CarMaintenance carMaintenance : result) {
				if (carMaintenance.getReturnDate() != null) {
					throw new BusinessException("Car is still under maintance!");
				}
			}
		}
	}

}
