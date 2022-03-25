package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCityDto;
import com.turkcell.rentACarProject.business.requests.city.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.city.UpdateCityRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface CityService {
	
	DataResult<List<GetCityDto>> getAll();

	DataResult<GetCityDto> getById(long id);

	Result add(CreateCityRequest createRequest);

	Result delete(long id);

	Result update(long id, UpdateCityRequest updateRequest);

}

