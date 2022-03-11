package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.list.ListAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarRentalDto;
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.DeleteCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;
import com.turkcell.rentACarProject.entities.concretes.CarRental;

@Service
public class CarRentalManager implements CarRentalService {

	private CarDao carDao;
	private CarRentalDao carRentalDao;
	private CarMaintenanceDao carMaintenanceDao;
	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarRentalManager(CarDao carDao, CarRentalDao carRentalDao, CarMaintenanceDao carMaintenanceDao,
			AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceDao = carMaintenanceDao;
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCarRentalDto>> getAll() {
		List<CarRental> result = carRentalDao.findAll();
		List<ListCarRentalDto> response = result.stream()
				.map(carRental -> modelMapperService.forDto().map(carRental, ListCarRentalDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarRentalDto>>(response);
	}

	@Override
	public DataResult<GetCarRentalDto> getById(int id) {
		CarRental carRental = carRentalDao.getById(id);
		if (!checkCarRentalIdExists(carRental.getId())) {
			return new ErrorDataResult<GetCarRentalDto>("CarRental.NotFound , Rental with this ID was not found!");
		}
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
//		carRental.setId(0);
		if (!checkIfCarExists(carRental.getCarId().getId())) {
			return new ErrorResult("CarRental.NotAdded , Car with this ID not exists!");
		}
		if (!checkIsUnderMaintenance(carRental)) {
			return new ErrorResult("CarRental.NotAdded , Car is under maintenance!");
		}
		this.carRentalDao.save(carRental);
		return new SuccessResult("CarRental.Added");
	}

	@Override
	public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException {
		CarRental carRental = this.modelMapperService.forRequest().map(deleteCarRentalRequest, CarRental.class);
		if (checkCarRentalIdExists(carRental.getId())) {
			this.carRentalDao.deleteById(carRental.getId());
			return new SuccessResult("CarRental.Deleted");
		}
		return new ErrorResult("CarRental.NotDeleted , Rental with given ID not exists!");
	}

	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException {
		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest, CarRental.class);
		if (!checkCarRentalIdExists(carRental.getId())) {
			return new ErrorResult("CarRental.NotUpdated , Rental with this ID not exists!");
		}
		if (!checkIsUnderMaintenance(carRental)) {
			return new ErrorResult("CarRental.NotUpdated , Car is under maintenance at requested times");
		}
//		if(checkIfAdditionalServiceId(carRental.getAd) {
//			return new ErrorResult("OrderedAdditionalService.NotUpdated , Ordered Additional Service with given ID not exists!");
//		}
		this.carRentalDao.save(carRental);
		return new SuccessResult("CarRental.Updated");
	}

	private boolean checkCarRentalIdExists(int carRentalId) {
		return Objects.nonNull(carRentalDao.getRentalsById(carRentalId));
	}

	private boolean checkIfCarExists(int carId) {
		return Objects.nonNull(carDao.getCarById(carId));
	}
	
	private boolean checkIfAdditionalServiceId(int additionalServiceId) {
		return Objects.nonNull(additionalServiceDao.getAdditionalServiceById(additionalServiceId));
	}

	private boolean checkIsUnderMaintenance(CarRental carRental) {
		List<CarMaintenance> result = this.carMaintenanceDao.getCarMaintenanceByCarId(carRental.getCarId());
		if (result != null) {
			for (CarMaintenance carMaintenance : result) {
				if (carRental.getReturnDate() != null) {
					return false;
				}
			}
		}
		return true;
	}

}
