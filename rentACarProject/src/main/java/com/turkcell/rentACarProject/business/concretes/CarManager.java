package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.ListCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
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
	public DataResult<List<ListCarDto>> getAll() {
		List<Car> result = this.carDao.findAll();
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response);
	}

	@Override
	public DataResult<GetCarDto> getById(int id) {
		Car car = this.carDao.getCarById(id);
		if (car != null) {
			GetCarDto response = this.modelMapperService.forDto().map(car, GetCarDto.class);
			return new SuccessDataResult<GetCarDto>(response, "Success");
		}
		return new ErrorDataResult<GetCarDto>("Car.NotFound");
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		String brandName = brandDao.findById(car.getBrand().getId()).get().getName();
		String colorName = colorDao.findById(car.getColor().getId()).get().getName();
		
		if (!checkIfCarDailyPriceLessThanZero(car.getDailyPrice()).isSuccess()) {
			return new ErrorResult("Car.NotAdded : " + brandName + "," + colorName);
		}
		if (!checkIfBrandId(car.getBrand().getId()).isSuccess()) {
			return new ErrorResult("Car.NotAdded : " + brandName + "," + colorName);
		}
		if (!checkIfColorId(car.getColor().getId()).isSuccess()) {
			return new ErrorResult("Car.NotAdded : " + brandName + "," + colorName);
		}
		if (!checkIfCarExists(car).isSuccess()) {
			return new ErrorResult("Car.NotAdded : " + brandName + "," + colorName);
		}
		this.carDao.save(car);
		return new SuccessResult("Car.Added : " + brandName + "," + colorName);
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		String brandName = brandDao.findById(car.getBrand().getId()).get().getName();
		String colorName = colorDao.findById(car.getColor().getId()).get().getName();
		if (checkCarIdExists(car.getId()).isSuccess()) {
			this.carDao.delete(car);
			return new SuccessResult("Car.Deleted : " + brandName + "," + colorName);
		}
		return new ErrorResult("Car.NotDeleted : " + brandName + "," + colorName);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		String brandName = brandDao.findById(car.getBrand().getId()).get().getName();
		String colorName = colorDao.findById(car.getColor().getId()).get().getName();
		if (checkCarIdExists(car.getId()).isSuccess()) {
		this.carDao.save(car);
		return new SuccessResult("Car.Updated : " + brandName + "," + colorName);
		}
		return new ErrorResult("Car.NotUpdated : " + brandName + "," + colorName);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response, "Success");
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction) {
		Sort sort = Sort.by(direction, "dailyPrice");
		// List<Car> result = this.carDao.findAll(Sort.by("dailyPrice").descending());
		List<Car> result = this.carDao.findAll(sort);
		List<ListCarDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarDto>>(response, "Success");
	}

	@Override
	public DataResult<List<ListCarDto>> getAllByDailyPriceLessThanEqual(double dailyPrice) {
		Sort sort = Sort.by(Sort.Direction.ASC, "dailyPrice");
		// List<Car> result = this.carDao.findAll(Sort.by("dailyPrice").ascending());
		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		if (!result.isEmpty()) {
			List<ListCarDto> response = result.stream()
					.map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class))
					.collect(Collectors.toList());
			return new SuccessDataResult<List<ListCarDto>>(response, "Success");
		}
		return new ErrorDataResult<List<ListCarDto>>("Car.NotFound");
	}

	private Result checkCarIdExists(int carId) {
		if (this.carDao.getCarById(carId) != null) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	private Result checkIfCarDailyPriceLessThanZero(double dailyPrice) {
		if (dailyPrice <= 0) {
			return new ErrorResult("Daily rental price cannot be less than or equal to 0.");
		}
		return new SuccessResult();
	}

	private Result checkIfBrandId(int brandId) {
		if (this.brandDao.getBrandById(brandId) != null) {
			return new SuccessResult();
		}
		return new ErrorResult("A brand with this ID was not found!");
	}

	private Result checkIfColorId(int colorId) {
		if (this.colorDao.getColorById(colorId) != null) {
			return new SuccessResult();
		}
		return new ErrorResult("A color with this ID was not found!");
	}

	private Result checkIfCarExists(Car car) {
		if (carDao.existsByDailyPrice(car.getDailyPrice()) && carDao.existsByModelYear(car.getModelYear())
				&& carDao.existsByDescription(car.getDescription()) && carDao.existsByBrand_Id(car.getBrand().getId())
				&& carDao.existsByColor_Id(car.getColor().getId())) {
			return new ErrorResult("Car already exists!");
		}
		return new SuccessResult();
	}

}