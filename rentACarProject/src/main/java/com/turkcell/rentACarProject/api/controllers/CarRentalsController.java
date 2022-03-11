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

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarRentalDto;
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.DeleteCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carRentals")
public class CarRentalsController {
	
	private CarRentalService carRentalService;
	
	@Autowired
	public CarRentalsController(CarRentalService carRentalService) {
		this.carRentalService = carRentalService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListCarRentalDto>> getAll() {
		return carRentalService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCarRentalDto> getById(@RequestParam int id) {
		return carRentalService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRentalRequest createCarRentalRequest) throws BusinessException {
		return this.carRentalService.add(createCarRentalRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarRentalRequest deleteCarRentalRequest)
			throws BusinessException {
		return this.carRentalService.delete(deleteCarRentalRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRentalRequest updateCarRentalRequest)
			throws BusinessException {
		return this.carRentalService.update(updateCarRentalRequest);
	}

}
