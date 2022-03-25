package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;


public interface CarMaintenanceService {

	DataResult<List<GetCarMaintenanceDto>> getAll();

	DataResult<GetCarMaintenanceDto> getById(long id);
	
	DataResult<List<GetCarMaintenanceDto>> getByCarId(long carId);

	Result add(CreateCarMaintenanceRequest createRequest);

	Result delete(long id);

	Result update(long id, UpdateCarMaintenanceRequest updateRequest);

}
