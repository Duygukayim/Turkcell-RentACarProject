package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.constants.CarStatus;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACarProject.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private BrandDao brandDao;
	private ColorDao colorDao;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, BrandDao brandDao, ColorDao colorDao) {
		
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.brandDao = brandDao;
		this.colorDao = colorDao;
	}

	@Override
	public DataResult<List<GetCarDto>> getAll() {
		
		List<Car> result = carDao.findAll();
		List<GetCarDto> response = result.stream().map(car -> modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarDto>>(response, Messages.CARLIST);
	}

	@Override
	public DataResult<GetCarDto> getById(long id) {
		
		checkCarIdExists(id);
		
		Car car = carDao.getById(id);
		GetCarDto response = modelMapperService.forDto().map(car, GetCarDto.class);
		
		return new SuccessDataResult<GetCarDto>(response, Messages.CARFOUND);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		checkIfBrandIdExists(createCarRequest.getBrandId());
		checkIfColorIdExists(createCarRequest.getColorId());
		checkIfCarDailyPriceLessThanZero(createCarRequest.getDailyPrice());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		
		checkIfCarExists(car);
		car.setStatus(CarStatus.AVAILABLE);
		this.carDao.save(car);
		
		return new SuccessResult(Messages.CARADD);
	}

	@Override
	public Result delete(long id) {
		
		checkCarIdExists(id);
		
		this.carDao.deleteById(id);
		
		return new SuccessResult(Messages.CARDELETE);
	}

	@Override
	public Result update(long id, UpdateCarRequest updateCarRequest) {
		
		checkCarIdExists(id);
		checkIfBrandIdExists(updateCarRequest.getBrandId());
		checkIfColorIdExists(updateCarRequest.getColorId());
		checkIfCarDailyPriceLessThanZero(updateCarRequest.getDailyPrice());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		
		checkIfCarExists(car);
		car.setStatus(CarStatus.AVAILABLE);
		car.setId(id);
		this.carDao.save(car);
		
		return new SuccessResult(Messages.CARUPDATE);
	}

	@Override
	public DataResult<List<GetCarDto>> getAllPaged(int pageNumber, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<GetCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarDto>>(response, Messages.CARPAGED);
	}

	@Override
	public DataResult<List<GetCarDto>> getAllSorted(Sort.Direction direction) {
		
		Sort sort = Sort.by(direction, "dailyPrice");
		
		List<Car> result = this.carDao.findAll(sort);
		List<GetCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCarDto>>(response, Messages.CARSORTED);
	}

	@Override
	public DataResult<List<GetCarDto>> getAllByDailyPriceLessThanEqual(double dailyPrice) {
		
		Sort sort = Sort.by(Sort.Direction.ASC, "dailyPrice");
		
		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
			if (!result.isEmpty()) {
				List<GetCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, GetCarDto.class)).collect(Collectors.toList());
			
				return new SuccessDataResult<List<GetCarDto>>(response, Messages.CARLIST);
		}
		return new ErrorDataResult<List<GetCarDto>>(Messages.CARNOTFOUND);
	}

	
	@Override
	public void setCarStatus(CarStatus status, long carId) {
		
		checkCarIdExists(carId);
		
		Car car = carDao.findById(carId);
		car.setStatus(status);
		
		new SuccessResult(Messages.CARSTATUS);
		
	}
	
	@Override
	public void setMileage(double returnMileage, long carId) {
		
		checkCarIdExists(carId);
		
		Car car = carDao.findById(carId);
		car.setMileage(returnMileage);
		
		new SuccessResult(Messages.CARMILEAGE);
	}
	
	
	private void checkCarIdExists(long carId) {
		
		if(!this.carDao.existsById(carId)) {
			
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}

	private void checkIfBrandIdExists(long brandId) {
		
		if(!this.brandDao.existsById(brandId)) {
			
			throw new BusinessException(Messages.BRANDNOTFOUND);
		}
	}

	private void checkIfColorIdExists(long colorId) {
		
		if(!this.colorDao.existsById(colorId)) {
			
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
	}

	private void checkIfCarDailyPriceLessThanZero(double dailyPrice) {
		
		if (dailyPrice <= 0) {
			
			throw new BusinessException(Messages.CARDAILYPRICEERROR);
		}
	}

	private void checkIfCarExists(Car car) {
		
		if (!Objects.nonNull(carDao.findByAllCar(car.getBrand().getId(), car.getColor().getId(), car.getModelYear(), car.getDailyPrice(), car.getDescription()))) {
			
			throw new BusinessException(Messages.CARALREADYEXISTS);
		}
	}


}
