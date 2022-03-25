package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface IndividualCustomerService {
	
	DataResult<List<GetIndividualCustomerDto>> getAll();

	DataResult<GetIndividualCustomerDto> getById(long id);
	
	Result add(CreateIndividualCustomerRequest createRequest);

	Result update(long id, UpdateIndividualCustomerRequest updateRequest);
	
	Result delete(long id);

}
