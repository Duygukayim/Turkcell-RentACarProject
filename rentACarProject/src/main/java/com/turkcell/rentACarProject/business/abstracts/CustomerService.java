package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.dtos.get.GetCustomerDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCustomerDto;
import com.turkcell.rentACarProject.business.requests.customer.CreateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.customer.DeleteCustomerRequest;
import com.turkcell.rentACarProject.business.requests.customer.UpdateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface CustomerService {

	DataResult<List<ListCustomerDto>> getAll();

	DataResult<GetCustomerDto> getById(int id);
	
	Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException;

	Result delete(DeleteCustomerRequest deleteCustomerRequest) throws BusinessException;

	Result update(UpdateCustomerRequest updateCustomerRequest) throws BusinessException;
	
}
