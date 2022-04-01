package com.turkcell.rentACarProject.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.constants.CarStatus;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CityDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACarProject.entities.concretes.CarRental;
import com.turkcell.rentACarProject.entities.concretes.Customer;

@Service
public class CarRentalManager implements CarRentalService {

	private CarDao carDao;
	private CarService carService;  //hem servis hem Dao kullanımı düzenlenecek
	private CityDao cityDao;
	private CarRentalDao carRentalDao;
	private CustomerDao customerDao;
	private PaymentService paymentService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private ModelMapperService modelMapperService;
	

	@Autowired
	public CarRentalManager(CarDao carDao, CarService carService, CityDao cityDao, CarRentalDao carRentalDao, CustomerDao customerDao, PaymentService paymentService, OrderedAdditionalServiceService orderedAdditionalServiceService, ModelMapperService modelMapperService) {
		
		this.carDao = carDao;
		this.carService = carService;
		this.cityDao = cityDao;
		this.carRentalDao = carRentalDao;
		this.customerDao = customerDao;
		this.paymentService = paymentService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<GetCarRentalDto>> getAll() {

		List<CarRental> result = carRentalDao.findAll();
		List<GetCarRentalDto> response = result.stream().map(carRental -> modelMapperService.forDto().map(carRental, GetCarRentalDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<GetCarRentalDto>>(response, Messages.CARRENTALLIST);
	}

	@Override
	public DataResult<GetCarRentalDto> getById(long id) {
		
		checkCarRentalIdExists(id);

		CarRental carRental = carRentalDao.getById(id);
		GetCarRentalDto response = modelMapperService.forDto().map(carRental, GetCarRentalDto.class);

		return new SuccessDataResult<GetCarRentalDto>(response, Messages.CARRENTALFOUND);
	}

	@Override
	public DataResult<List<GetCarRentalDto>> getByCarId(long carId) {

		List<CarRental> result = this.carRentalDao.findByCar_Id(carId);
		List<GetCarRentalDto> response = result.stream().map(carRental -> this.modelMapperService.forDto().map(carRental, GetCarRentalDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<GetCarRentalDto>>(response, Messages.CARFOUND);
	}
	
	
	@Override
	public DataResult<List<GetCarRentalDto>> getByCustomerId(long customerId) {
		
		List<CarRental> result = this.carRentalDao.findByCustomer_UserId(customerId);
		List<GetCarRentalDto> response = result.stream().map(carRental -> this.modelMapperService.forDto().map(carRental, GetCarRentalDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarRentalDto>>(response, Messages.CUSTOMERFOUND);
	}


	@Override
	public Result addForCorporateCustomer(CreateCarRentalRequest createCarRentalRequest) {

		checkIfCarIdExists(createCarRentalRequest.getCarId());
		checkIfCustomerIdExists(createCarRentalRequest.getCustomerId());
		checkIfCityIdExists(createCarRentalRequest.getRentCityId());
		checkIfCityIdExists(createCarRentalRequest.getReturnCityId());
		checkCarStatus(createCarRentalRequest.getCarId());
		
		CarRental carRental  = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		
		Customer customer = new Customer();
        customer.setUserId(createCarRentalRequest.getCustomerId());
        carRental .setCustomer(customer);
        
        carRental .setStartingMileage(carService.getById(createCarRentalRequest.getCarId()).getData().getMileage());
        carRental .setReturnMileage(carService.getById(createCarRentalRequest.getCarId()).getData().getMileage());
        
        carRental = this.carRentalDao.saveAndFlush(carRental);

        this.orderedAdditionalServiceService.add(createCarRentalRequest.getCreatedOrderedAdditionalServiceRequestSet(), carRental.getId());
        carRental.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getByCarRentalId(carRental.getId()));

        carService.setCarStatus(CarStatus.RENTED, carRental.getCar().getId());

		return new SuccessResult(Messages.CARRENTALADDFORCORPORATE);
	}

	@Override
	public Result addForIndividualCustomer(CreateCarRentalRequest createCarRentalRequest) {
		
		checkIfCarIdExists(createCarRentalRequest.getCarId());
		checkIfCustomerIdExists(createCarRentalRequest.getCustomerId());
		checkIfCityIdExists(createCarRentalRequest.getRentCityId());
		checkIfCityIdExists(createCarRentalRequest.getReturnCityId());
		checkCarStatus(createCarRentalRequest.getCarId());
		
		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		
		Customer customer = new Customer();
        customer.setUserId(createCarRentalRequest.getCustomerId());
        carRental.setCustomer(customer);
        
        carRental.setStartingMileage(carService.getById(createCarRentalRequest.getCarId()).getData().getMileage());
        carRental.setReturnMileage(carService.getById(createCarRentalRequest.getCarId()).getData().getMileage());
        
        carRental = this.carRentalDao.saveAndFlush(carRental);

        this.orderedAdditionalServiceService.add(createCarRentalRequest.getCreatedOrderedAdditionalServiceRequestSet(), carRental.getId());
        carRental.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getByCarRentalId(carRental.getId()));

        carService.setCarStatus(CarStatus.RENTED, carRental.getCar().getId());

		return new SuccessResult(Messages.CARRENTALADDFORINDIVIDUAL);
	}

	@Override
	public Result delete(long id) {
		
		checkCarRentalIdExists(id);
		
		long carId = carRentalDao.findById(id).getCar().getId();

		this.carRentalDao.deleteById(id);
		carService.setCarStatus(CarStatus.AVAILABLE, carId);

		return new SuccessResult(Messages.CARRENTALDELETE);
	}

	@Override
	public Result update(long id, UpdateCarRentalRequest updateCarRentalRequest) {
		
		checkCarRentalIdExists(id);
		checkIfCustomerIdExists(updateCarRentalRequest.getCustomerId());
		checkIfCityIdExists(updateCarRentalRequest.getRentCityId());
		checkIfCityIdExists(updateCarRentalRequest.getReturnCityId());
		
		LocalDate returnDate = carRentalDao.findById(id).getReturnDate();
        long oldReturnCityId = carRentalDao.findById(id).getReturnCity().getId();
		
		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest, CarRental.class);
		carRental.setId(id);
		carRental.setStartingMileage(carService.getById(updateCarRentalRequest.getCarId()).getData().getMileage());
        carService.setMileage(updateCarRentalRequest.getReturnMileage(), updateCarRentalRequest.getCarId());
		
        Customer customer = new Customer();
	    customer.setUserId(updateCarRentalRequest.getCustomerId());
	    carRental.setCustomer(customer);
		
		this.carRentalDao.save(carRental);
		
		calExtraRentedTotal(id, returnDate, updateCarRentalRequest.getReturnDate(), oldReturnCityId, updateCarRentalRequest.getCreatePaymentRequest(), updateCarRentalRequest.isRememberMe());
		
		carService.setCarStatus(CarStatus.AVAILABLE, updateCarRentalRequest.getCarId());

		return new SuccessResult(Messages.CARRENTALUPDATE);
	}
	
	
	@Override
	public double calTotalPrice(long carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);
		
		return (calTotalDailyPriceForRentalCar(carRentalId) + calTotalDailyPriceForAdditionalService(carRentalId)) * calTotalDaysForRental(carRental.getRentDate(), carRental.getReturnDate()) + calDifferentCityPrice(carRentalId);
	}
	

	private int calTotalDaysForRental(LocalDate startDate, LocalDate endDate) {

		if (ChronoUnit.DAYS.between(startDate, endDate) == 0) {
			
			return 1;
		}

		return Integer.valueOf((int) ChronoUnit.DAYS.between(startDate, endDate));
	}
	

	private double calDifferentCityPrice(long carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);

		if (carRental.getRentCity().getId() != carRental.getReturnCity().getId()) {
			return 750;
		}

		return 0;
	}
	

	private double calTotalDailyPriceForRentalCar(long carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);

		return carRental.getCar().getDailyPrice();
	}
	

	private double calTotalDailyPriceForAdditionalService(long carRentalId) {
		
		CarRental carRental = carRentalDao.getById(carRentalId);

		return this.orderedAdditionalServiceService.calDailyTotal(carRental.getOrderedAdditionalServices());

	}
	
	private void calExtraRentedTotal(long id, LocalDate oldReturnDate, LocalDate newReturnDate, long oldReturnCityId, CreatePaymentRequest createPaymentRequest, boolean rememberMe) {
	     
		CarRental carRental = carRentalDao.findById(id);

	     double newTotal = (carRental.getCar().getDailyPrice()
	              + this.orderedAdditionalServiceService.calDailyTotal(carRental.getOrderedAdditionalServices()))
	              * calTotalDaysForRental(oldReturnDate, newReturnDate)
	              + checkReturnCities(carRental.getRentCity().getId(), oldReturnCityId, carRental.getReturnCity().getId());

	     this.paymentService.addForExtra(createPaymentRequest, rememberMe, newTotal);
	}
	
	private double checkReturnCities(long rentCity, long oldReturnCityId, long newReturnCityId) {
	   
		if (rentCity == oldReturnCityId) {
	       
			if (oldReturnCityId != newReturnCityId)
	           
				return 750.0;
		
	    } else {
	        
	    	if (rentCity == newReturnCityId)
	           
	    		return -750.0;
	    }	
	    return 0.0;
	}
	
	private void checkCarStatus(long carId) {
		
		if (this.carService.getById(carId).getData().getStatus() == CarStatus.RENTED)
			
			throw new BusinessException(Messages.CARISRENTED);
		
		else if (this.carService.getById(carId).getData().getStatus() == CarStatus.UNDER_MAINTENANCE)
			
			throw new BusinessException(Messages.CARISUNDERMAINTENANCE);
		
		else if (this.carService.getById(carId).getData().getStatus() == CarStatus.DAMAGED)
			
			throw new BusinessException(Messages.CARISDAMAGED);
	}
	
	private void checkCarRentalIdExists(long carRentalId) {
		
		if (!this.carRentalDao.existsById(carRentalId)) {
			
			throw new BusinessException("Car Rental with this ID was not found!");
		}
	}
	
	private void checkIfCarIdExists(long carId) {
		
		if (!this.carDao.existsById(carId)) {
			
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}
	
	private void checkIfCityIdExists(long cityId) {
		
		if (!this.cityDao.existsById(cityId)) {
			
			throw new BusinessException(Messages.CITYNOTFOUND);
		}
	}
	
	private void checkIfCustomerIdExists(long customerId) {
		
		if (!this.customerDao.existsById(customerId)) {
			
			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}
	}
	
}


