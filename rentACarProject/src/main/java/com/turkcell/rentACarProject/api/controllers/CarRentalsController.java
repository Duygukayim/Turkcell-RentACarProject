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
import com.turkcell.rentACarProject.business.requests.carRental.CreateCarRentalRequest;
import com.turkcell.rentACarProject.business.requests.carRental.UpdateCarRentalRequest;
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
	
	@GetMapping("/get/all")
	public DataResult<List<GetCarRentalDto>> getAll() {
		
		return carRentalService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCarRentalDto> getById(@RequestParam long id) {
		
		return carRentalService.getById(id);
	}

	@PostMapping("/createCorporateCustomer")
	public Result createCorporateCustomer(@RequestBody @Valid CreateCarRentalRequest createRequest) {
		
		return this.carRentalService.createForCorporateCustomer(createRequest);
	}
	
	@PostMapping("/createIndividualCustomer")
	public Result createIndividualCustomer(@RequestBody @Valid CreateCarRentalRequest createRequest) {
		
		return this.carRentalService.createForIndividualCustomer(createRequest);
	}
	
	@GetMapping("/getByCarId")
	public DataResult<List<GetCarRentalDto>> getByCarId(@RequestParam long carId) {
	        
		 return carRentalService.getByCarId(carId);
	}
	
	@GetMapping("/getByCustomerId")
	public DataResult<List<GetCarRentalDto>> getByCustomerId(@RequestParam long customerId) {
	        
		 return carRentalService.getByCustomerId(customerId);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id) {
		
		return this.carRentalService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam long id, @RequestBody @Valid UpdateCarRentalRequest updateRequest) {
		
		return this.carRentalService.update(id, updateRequest);
	}

}
