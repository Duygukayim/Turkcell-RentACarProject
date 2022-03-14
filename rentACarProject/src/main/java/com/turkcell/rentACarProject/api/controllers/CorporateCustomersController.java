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
import com.turkcell.rentACarProject.business.dtos.list.ListCorporateCustomerDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
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
	
	@GetMapping("/getAll")
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		return corporateCustomerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCorporateCustomerDto> get(@RequestParam int id) throws BusinessException {
		
		return corporateCustomerService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		return corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		return corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		return corporateCustomerService.update(updateCorporateCustomerRequest);
	}


}
