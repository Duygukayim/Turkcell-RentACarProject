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

import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.dtos.get.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {
	
	private final IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}
	
	@GetMapping("/get/all")
	public DataResult<List<GetIndividualCustomerDto>> getAll() {
		
		return individualCustomerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetIndividualCustomerDto> get(@RequestParam long id) {
		
		return individualCustomerService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createRequest) {
		
		return individualCustomerService.add(createRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id) {
		
		return individualCustomerService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam long id, @RequestBody @Valid UpdateIndividualCustomerRequest updateRequest) {
		
		return individualCustomerService.update(id, updateRequest);
	}
	
}
