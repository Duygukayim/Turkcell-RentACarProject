package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.dtos.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.ListCarRentalDto;
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
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarMaintenance;
import com.turkcell.rentACarProject.entities.concretes.CarRental;

@Service
public class CarRentalManager implements CarRentalService {

	private CarDao carDao;
	private CarRentalDao carRentalDao;
	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarRentalManager(CarDao carDao, CarRentalDao carRentalDao, CarMaintenanceDao carMaintenanceDao,
			ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceDao = carMaintenanceDao;
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
		if (carRental != null) {
			GetCarRentalDto response = modelMapperService.forDto().map(carRental, GetCarRentalDto.class);
			return new SuccessDataResult<GetCarRentalDto>(response, "Success");
		}
		return new ErrorDataResult<GetCarRentalDto>("CarRental.NotFound");
	}

	@Override
	public Result add(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {
		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
//		carRental.setId(0);
		if (!checkIfCarExists(carRental.getCarId().getId()).isSuccess()) {
			return new ErrorResult("CarRental.NotAdded , Car ID does not exist!");
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
		if (checkCarRentalIdExists(carRental.getId()).isSuccess()) {
			this.carRentalDao.deleteById(carRental.getId());
			return new SuccessResult("CarRental.Deleted");
		}
		return new ErrorResult("CarRental.NotDeleted , Rental with given Id not exists");
	}

	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException {
		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest, CarRental.class);
		if (!checkCarRentalIdExists(carRental.getId()).isSuccess()) {
			return new ErrorResult("CarRental.NotUpdated , Rental with given Id not exists");
		}
		if (!checkIsUnderMaintenance(carRental)) {
			return new ErrorResult("CarRental.NotUpdated , Car is under maintenance at requested times");
		}
		this.carRentalDao.save(carRental);
		return new SuccessResult("CarRental.Updated");
	}

	private Result checkCarRentalIdExists(int id) {
		if (this.carRentalDao.getById(id) != null) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	private Result checkIfCarExists(int carId) {
		if (this.carDao.getCarById(carId) != null) {
			return new SuccessResult();
		}
		return new ErrorResult("A car with this ID does not exist!");
	}

	private boolean checkIsUnderMaintenance(CarRental carRental) {
		List<CarMaintenance> result = this.carMaintenanceDao.getCarMaintenanceByCarId(carRental.getCarId());
		if (result != null) {
			for (CarMaintenance carMaintenance : result) {
				if (carRental.getRentDate().isBefore(carMaintenance.getReturnDate())
						|| carRental.getReturnDate().isBefore(carMaintenance.getReturnDate())) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public DataResult<List<GetCarRentalDto>> getByCarId(int id) {
		Car car = this.carDao.getById(id);
        List<CarRental> result = this.carRentalDao.getCarRentalsByCarId(car);
        List<GetCarRentalDto> response = result.stream().map(rental -> this.modelMapperService.forDto().map(rental, GetCarRentalDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<GetCarRentalDto>>(response , "Success");
	}

}
