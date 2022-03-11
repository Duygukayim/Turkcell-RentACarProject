package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.dtos.get.GetCustomerDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCustomerDto;
import com.turkcell.rentACarProject.business.requests.customer.CreateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.customer.DeleteCustomerRequest;
import com.turkcell.rentACarProject.business.requests.customer.UpdateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACarProject.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {
	
	private CustomerDao customerDao;
	private ModelMapperService modelMapperService;

	public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCustomerDto>> getAll() {
		List<Customer> result = customerDao.findAll();
		 if (result.isEmpty()) {
	            return new ErrorDataResult<List<ListCustomerDto>>("Customer.NotListed");
	        }
		List<ListCustomerDto> response = result.stream()
				.map(customer -> modelMapperService.forDto().map(customer, ListCustomerDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListCustomerDto>>(response, "Success");
	}

	@Override
	public DataResult<GetCustomerDto> getById(int id) {
		Customer customer = customerDao.getById(id);
		if (checkIfCustomerId(customer.getId())) {
			GetCustomerDto response = modelMapperService.forDto().map(customer, GetCustomerDto.class);
			return new SuccessDataResult<GetCustomerDto>(response, "Success");
		}
		return new ErrorDataResult<GetCustomerDto>("Customer.NotFounded , A customer with this ID was not found!");
	}

	@Override
	public Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException {
		Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);
		this.customerDao.save(customer);
		return new SuccessDataResult<CreateCustomerRequest>("Customer.Added : " + customer.getFirstName());
	}

	@Override
	public Result delete(DeleteCustomerRequest deleteCustomerRequest) throws BusinessException {
		Customer customer = this.modelMapperService.forRequest().map(deleteCustomerRequest, Customer.class);
		if (checkIfCustomerId(customer.getId())) {
			this.customerDao.delete(customer);
			return new SuccessResult("Customer.Deleted : " + customer.getFirstName());
		}
		return new ErrorResult("Customer.NotDeleted , A customer with this ID was not found!");
	}

	@Override
	public Result update(UpdateCustomerRequest updateCustomerRequest) throws BusinessException {
		Customer customer = this.modelMapperService.forRequest().map(updateCustomerRequest, Customer.class);
		if (checkIfCustomerId(customer.getId())) {
			this.customerDao.save(customer);
			return new SuccessResult("Customer.Updated : " + customer.getFirstName());
		}
		return new ErrorResult("Customer.NotUpdated : , A customer with this ID was not found!");
	}
	
	private boolean checkIfCustomerId(int customerId) {
		return Objects.nonNull(customerDao.getCustomerById(customerId));
	}

}
