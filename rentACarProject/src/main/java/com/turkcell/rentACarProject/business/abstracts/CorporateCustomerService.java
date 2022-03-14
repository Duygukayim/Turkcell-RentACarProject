package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCorporateCustomerDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface CorporateCustomerService {
	
	DataResult<List<ListCorporateCustomerDto>> getAll();

	DataResult<GetCorporateCustomerDto> getById(int id) throws BusinessException;
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;

}