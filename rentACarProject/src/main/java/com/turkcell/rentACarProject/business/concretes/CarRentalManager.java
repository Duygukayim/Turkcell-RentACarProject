package com.turkcell.rentACarProject.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.constants.Messages;
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
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CityDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;
import com.turkcell.rentACarProject.entities.concretes.CarRental;

@Service
public class CarRentalManager implements CarRentalService {

	private CarDao carDao;
	private CityDao cityDao;
	private CarRentalDao carRentalDao;
	private CarMaintenanceDao carMaintenanceDao;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarRentalManager(CarDao carDao, CityDao cityDao, CarRentalDao carRentalDao, CarMaintenanceDao carMaintenanceDao, OrderedAdditionalServiceService orderedAdditionalServiceService, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.cityDao = cityDao;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceDao = carMaintenanceDao;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCarRentalDto>> getAll() {

		List<CarRental> result = carRentalDao.findAll();
		List<ListCarRentalDto> response = result.stream().map(carRental -> modelMapperService.forDto().map(carRental, ListCarRentalDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarRentalDto>>(response, Messages.CARRENTALLIST);
	}

	@Override
	public DataResult<GetCarRentalDto> getById(int id) throws BusinessException {

		CarRental carRental = carRentalDao.getById(id);
		checkCarRentalIdExists(carRental.getId());
		GetCarRentalDto response = modelMapperService.forDto().map(carRental, GetCarRentalDto.class);

		return new SuccessDataResult<GetCarRentalDto>(response, Messages.CARRENTALFOUND);
	}

	@Override
	public DataResult<List<GetCarRentalDto>> getByCarId(int id) {

		Car car = this.carDao.getById(id);
		List<CarRental> result = this.carRentalDao.findByCar_Id(car.getId());
		List<GetCarRentalDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, GetCarRentalDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<GetCarRentalDto>>(response, Messages.CARLIST);
	}

	@Override
	public Result createForCorporateCustomer(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {

		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		checkIfCarIdExists(carRental.getCar().getId());
		checkIfCarIsRented(carRental.getCar().getId(), carRental.getRentDate());
		checkIfCityIdExists(carRental.getRentCity().getId());
		checkIfCityIdExists(carRental.getReturnCity().getId());
		checkUnderMaintenance(carRental);
		this.carRentalDao.save(carRental);

		return new SuccessResult(Messages.CARRENTALADDFORCORPORATE);
	}

	@Override
	public Result createForIndividualCustomer(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {

		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		checkIfCarIdExists(carRental.getCar().getId());
		checkIfCityIdExists(carRental.getRentCity().getId());
		checkIfCityIdExists(carRental.getReturnCity().getId());
		checkUnderMaintenance(carRental);
		this.carRentalDao.save(carRental);

		return new SuccessResult(Messages.CARRENTALADDFORINDIVIDUAL);
	}

	@Override
	public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) {

		CarRental carRental = this.modelMapperService.forRequest().map(deleteCarRentalRequest, CarRental.class);
		checkCarRentalIdExists(carRental.getId());
		this.carRentalDao.deleteById(carRental.getId());

		return new SuccessResult(Messages.CARRENTALDELETE);
	}

	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) {

		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest, CarRental.class);
		checkCarRentalIdExists(carRental.getId());
		checkUnderMaintenance(carRental);
		carRental.getCar().setKilometerInfo(updateCarRentalRequest.getReturnKilometer());
		this.carRentalDao.save(carRental);

		return new SuccessResult(Messages.CARRENTALUPDATE);
	}

	private void checkCarRentalIdExists(int carRentalId) {
		if (!this.carRentalDao.existsById(carRentalId)) {
			throw new BusinessException("Car Rental with this ID was not found!");
		}
	}

	private void checkIfCarIdExists(int carId) {
		if (!this.carDao.existsById(carId)) {
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}

	private void checkIfCityIdExists(int cityId) {

		if (!this.cityDao.existsById(cityId)) {
			throw new BusinessException(Messages.CITYNOTFOUND);
		}
	}

	private void checkUnderMaintenance(CarRental carRental) {

		List<CarMaintenance> result = this.carMaintenanceDao.findByCar_Id(carRental.getCar().getId());
		if (result != null) {
			for (CarMaintenance carMaintenance : result) {
				if (carMaintenance.getReturnDate() != null) {
					throw new BusinessException(Messages.CARRENTALMAINTENANCEERROR);
				}
			}
		}
	}

	@Override
	public DataResult<CarRental> getCarRentalByRentalId(int id) {

		return new SuccessDataResult<CarRental>(this.carRentalDao.getById(id));
	}

	
	@Override
	public double calTotalPriceForInvoice(int carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);
		
		return (calTotalDailyPriceForRentalCar(carRentalId) + calTotalDailyPriceForAdditionalService(carRentalId)) * calTotalDaysForRental(carRental.getRentDate(), carRental.getReturnDate()) + calDifferentCityPrice(carRentalId);
	}
	

	private void checkIfCarIsRented(int carId, LocalDate startingDate) {

		List<CarRental> carRentals = this.carRentalDao.findByCar_Id(carId);

		for (CarRental carRental : carRentals) {

			if (startingDate.isAfter(carRental.getReturnDate())) {

				throw new BusinessException(Messages.CARRENTALERROR);
			}
		}
	}
	

	private int calTotalDaysForRental(LocalDate startDate, LocalDate endDate) {

		if (ChronoUnit.DAYS.between(startDate, endDate) == 0) {
			return 1;
		}

		return Integer.valueOf((int) ChronoUnit.DAYS.between(startDate, endDate));
	}
	

	private double calDifferentCityPrice(int carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);

		if (carRental.getRentCity().getId() != carRental.getReturnCity().getId()) {
			return 750;
		}

		return 0;
	}
	

	private double calTotalDailyPriceForRentalCar(int carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);

		return carRental.getCar().getDailyPrice();
	}
	

	private double calTotalDailyPriceForAdditionalService(int carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);

		return this.orderedAdditionalServiceService.calDailyTotal(carRental.getOrderedAdditionalServices());

	}
	

}
