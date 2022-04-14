package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarMaintenanceDto;
import com.turkcell.rentACarProject.business.requests.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACarProject.business.requests.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {

	private final CarMaintenanceService carMaintenanceService;

	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	@GetMapping("/get/all")
	public DataResult<List<GetCarMaintenanceDto>> getAll() {
		
		return carMaintenanceService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCarMaintenanceDto> getById(@RequestParam long id) {
		
		return carMaintenanceService.getById(id);
	}

	@GetMapping("getByCarId")
	public DataResult<List<GetCarMaintenanceDto>> getByCarId(@RequestParam long carId) {
		
		return this.carMaintenanceService.getByCarId(carId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createRequest) {
		
		return this.carMaintenanceService.add(createRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id) {
		
		return this.carMaintenanceService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam long id, @RequestBody @Valid UpdateCarMaintenanceRequest updateRequest) {
		
		return this.carMaintenanceService.update(id, updateRequest);
	}

}
