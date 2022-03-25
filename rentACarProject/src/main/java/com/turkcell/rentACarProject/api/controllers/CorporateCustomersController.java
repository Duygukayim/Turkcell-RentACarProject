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

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {
	
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}
	
	@GetMapping("/get/all")
	public DataResult<List<GetCorporateCustomerDto>> getAll() {
		
		return corporateCustomerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCorporateCustomerDto> get(@RequestParam long id) {
		
		return corporateCustomerService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createRequest){
		
		return corporateCustomerService.add(createRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id) {
		
		return corporateCustomerService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam  long id, @RequestBody @Valid UpdateCorporateCustomerRequest updateRequest) {
		
		return corporateCustomerService.update(id, updateRequest);
	}


}
