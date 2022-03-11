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

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.dtos.get.GetCustomerDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCustomerDto;
import com.turkcell.rentACarProject.business.requests.customer.CreateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.customer.DeleteCustomerRequest;
import com.turkcell.rentACarProject.business.requests.customer.UpdateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
	
	private CustomerService customerService;

	@Autowired
    public CustomersController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/getAll")
   public DataResult<List<ListCustomerDto>> getAll() throws BusinessException {
        return customerService.getAll();
    }
    
    @GetMapping("/getById")
    public DataResult<GetCustomerDto> getById(@RequestParam int id) throws BusinessException{
    	return customerService.getById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) throws BusinessException{
        return this.customerService.add(createCustomerRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam @Valid DeleteCustomerRequest deleteCustomerRequest) throws BusinessException{
    	return this.customerService.delete(deleteCustomerRequest);
    }
    
    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) throws BusinessException{
        return this.customerService.update(updateCustomerRequest);
    }
    
}
