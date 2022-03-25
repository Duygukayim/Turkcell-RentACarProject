package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface AdditionalServiceService {

	DataResult<List<GetAdditionalServiceDto>> getAll();

	DataResult<GetAdditionalServiceDto> getById(long id);
	
	Result add(CreateAdditionalServiceRequest createRequest);

	Result delete(long id);

	Result update(long id, UpdateAdditionalServiceRequest updateRequest);
	
}
