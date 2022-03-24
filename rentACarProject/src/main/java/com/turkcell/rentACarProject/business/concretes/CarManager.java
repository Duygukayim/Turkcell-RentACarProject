package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.DeleteCarRequest;
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
	public DataResult<List<ListCarDto>> getAll() {
		
		List<Car> result = carDao.findAll();
		List<ListCarDto> response = result.stream().map(car -> modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response, Messages.CARLIST);
	}

	@Override
	public DataResult<GetCarDto> getById(int id) throws BusinessException {
		
		Car car = carDao.getById(id);
		checkCarIdExists(car.getId());
		GetCarDto response = modelMapperService.forDto().map(car, GetCarDto.class);
		
		return new SuccessDataResult<GetCarDto>(response, Messages.CARFOUND);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		checkIfBrandIdExists(createCarRequest.getBrandId());
		checkIfColorIdExists(createCarRequest.getColorId());
		checkIfCarDailyPriceLessThanZero(createCarRequest.getDailyPrice());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		String brandName = brandDao.findById(car.getBrand().getId()).get().getName();
		String colorName = colorDao.findById(car.getColor().getId()).get().getName();
		
		checkIfCarExists(car);
		this.carDao.save(car);
		
		return new SuccessResult(Messages.CARADD + " " + brandName + " " + colorName);
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		
		checkCarIdExists(deleteCarRequest.getId());
		
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		String brandName = brandDao.findById(car.getBrand().getId()).get().getName();
		String colorName = colorDao.findById(car.getColor().getId()).get().getName();
		
		this.carDao.delete(car);
		
		return new SuccessResult(Messages.CARDELETE + " " + brandName + " " + colorName);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		
		checkCarIdExists(updateCarRequest.getId());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		String brandName = brandDao.findById(car.getBrand().getId()).get().getName();
		String colorName = colorDao.findById(car.getColor().getId()).get().getName();
		
		this.carDao.save(car);
		
		return new SuccessResult(Messages.CARUPDATE + " " + brandName + " " + colorName);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response, Messages.CARPAGED);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted(Sort.Direction direction) {
		
		Sort sort = Sort.by(direction, "dailyPrice");
		List<Car> result = this.carDao.findAll(sort);
		List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response, Messages.CARSORTED);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllByDailyPriceLessThanEqual(double dailyPrice) {
		
		Sort sort = Sort.by(Sort.Direction.ASC, "dailyPrice");
		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
			if (!result.isEmpty()) {
				List<ListCarDto> response = result.stream().map(car -> this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
			
				return new SuccessDataResult<List<ListCarDto>>(response, Messages.CARFOUND);
		}
		return new ErrorDataResult<List<ListCarDto>>(Messages.CARNOTFOUND);
	}

	private void checkCarIdExists(int carId) {
		
		if(!this.carDao.existsById(carId)) {
			throw new BusinessException(Messages.CARNOTFOUND);
		}
	}

	private void checkIfBrandIdExists(int brandId) {
		
		if(!this.brandDao.existsById(brandId)) {
			throw new BusinessException(Messages.BRANDNOTFOUND);
		}
	}

	private void checkIfColorIdExists(int colorId) {
		
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
		
		if (carDao.existsByDailyPrice(car.getDailyPrice()) && carDao.existsByModelYear(car.getModelYear())
				&& carDao.existsByDescription(car.getDescription()) && carDao.existsByBrand_Id(car.getBrand().getId())
				&& carDao.existsByColor_Id(car.getColor().getId())) {
			throw new BusinessException(Messages.CARALREADYEXISTS);
		}
	}
	
	@Override
    public void setOperationForKilometer(int carId, int kilometer) throws BusinessException {

        Car car = this.carDao.getById(carId);
        car.setKilometerInfo(kilometer);
        this.carDao.save(car);
    }

}
