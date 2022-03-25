package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface CorporateCustomerService {
	
	DataResult<List<GetCorporateCustomerDto>> getAll();

	DataResult<GetCorporateCustomerDto> getById(long id);
	
	Result add(CreateCorporateCustomerRequest createRequest);

	Result update(long id, UpdateCorporateCustomerRequest updateRequest);
	
	Result delete(long id);

}

