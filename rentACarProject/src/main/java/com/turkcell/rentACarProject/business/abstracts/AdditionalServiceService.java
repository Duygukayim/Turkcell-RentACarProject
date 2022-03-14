package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface AdditionalServiceService {

	DataResult<List<ListAdditionalServiceDto>> getAll();

	DataResult<GetAdditionalServiceDto> getById(int id) throws BusinessException;
	
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;

	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
	
}
