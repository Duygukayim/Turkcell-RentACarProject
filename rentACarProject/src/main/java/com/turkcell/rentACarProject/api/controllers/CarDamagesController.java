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

import com.turkcell.rentACarProject.business.abstracts.CarDamageService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarDamageDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarDamageDto;
import com.turkcell.rentACarProject.business.requests.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACarProject.business.requests.carDamage.DeleteCarDamageRequest;
import com.turkcell.rentACarProject.business.requests.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carDamages")
public class CarDamagesController {
	
	private CarDamageService carDamageService;
	
	@Autowired
	public CarDamagesController(CarDamageService carDamageService) {
		this.carDamageService = carDamageService;
	}

	@GetMapping("/getAll")
	public DataResult<List<ListCarDamageDto>> getAll() {
		
		return carDamageService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCarDamageDto> getById(@RequestParam int id){
		
		return carDamageService.getById(id);
	}

	@GetMapping("getByCarId")
	public DataResult<List<ListCarDamageDto>> getByCarId(@RequestParam int carId) {
		
		return this.carDamageService.getByCarId(carId);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarDamageRequest createCarMaintenanceRequest) {
		
		return this.carDamageService.add(createCarMaintenanceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarDamageRequest deleteCarDamageRequest) {
		
		return this.carDamageService.delete(deleteCarDamageRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) {
		
		return this.carDamageService.update(updateCarDamageRequest);
	}


}
