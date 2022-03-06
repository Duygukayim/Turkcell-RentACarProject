package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.dtos.ListCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
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
		List<CarMaintenance> carMaintenances = this.carMaintenanceDao.findAll();

		List<ListCarMaintenanceDto> carMaintenanceListDtos = carMaintenances.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, ListCarMaintenanceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListCarMaintenanceDto>>(carMaintenanceListDtos, "Data Listed");
	}

	@Override
	public DataResult<GetCarMaintenanceDto> getById(int id) {
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
		if (carMaintenance != null) {
			GetCarMaintenanceDto response = this.modelMapperService.forDto().map(carMaintenance,
					GetCarMaintenanceDto.class);
			return new SuccessDataResult<GetCarMaintenanceDto>(response, "Success");
		}
		return new ErrorDataResult<GetCarMaintenanceDto>("CarMaintenance.NotFound");
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		System.out.println(carMaintenance);
		carMaintenance.setId(0);
		if (!checkIfCarExists(carMaintenance.getCarId()).isSuccess()) {
			return new ErrorResult("CarMaintenance.NotAdded , Car ID does not exist!");
		}
		if (!checkIsRented(carMaintenance)) {
			return new ErrorResult("CarMaintenance.NotAdded , Car is already rented!");
		}

		this.carMaintenanceDao.save(carMaintenance);
		return new SuccessResult("CarMaintenance.Added");
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,
				CarMaintenance.class);
		if (checkCarMaintenanceIdExists(carMaintenance.getId()).isSuccess()) {
			this.carMaintenanceDao.deleteById(carMaintenance.getId());
			return new SuccessResult("CarMaintenance.Deleted");
		}
		return new ErrorResult("CarMaintenance.NotDeleted");
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		if (checkCarMaintenanceIdExists(carMaintenance.getId()).isSuccess()) {
			this.carMaintenanceDao.save(carMaintenance);
			return new SuccessResult("CarMaintenance.Updated");
		}
		return new ErrorResult("CarMaintenance.NotUpdated");
	}

//	@Override
//	public DataResult<List<ListCarMaintenanceDto>> getAllPaged(int pageNumber, int pageSize) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public DataResult<List<ListCarMaintenanceDto>> getAllSorted(Direction direction) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private Result checkCarMaintenanceIdExists(int id) {
		if (this.carMaintenanceDao.getById(id) != null) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	private Result checkIfCarExists(Car car) {
		if (this.carDao.getCarById(car.getId()) != null) {
			return new SuccessResult();
		}
		return new ErrorResult("A car with this ID does not exist!");
	}

	private boolean checkIsRented(CarMaintenance carMaintenance) {
		List<CarRental> result = this.carRentalDao.getCarRentalsByCarId(carMaintenance.getCarId().getId());
		if (result != null) {
			for (CarRental rental : result) {
				if (rental.getReturnDate() != null 
						&& carMaintenance.getReturnDate().isAfter(rental.getRentDate())
						&& carMaintenance.getReturnDate().isBefore((rental.getReturnDate()))) {
					return false;
				}
				if (rental.getReturnDate() == null 
						&& carMaintenance.getReturnDate().isAfter(rental.getRentDate())
						|| carMaintenance.getReturnDate().equals(rental.getRentDate())) {
					return false;
				}
			}
		}
		return true;
	}

}
