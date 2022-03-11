package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.dtos.get.GetCityDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCityDto;
import com.turkcell.rentACarProject.business.requests.city.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.city.DeleteCityRequest;
import com.turkcell.rentACarProject.business.requests.city.UpdateCityRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface CityService {
	
	DataResult<List<ListCityDto>> getAll();

	DataResult<GetCityDto> getById(int id);

	Result add(CreateCityRequest createCityRequest);

	Result delete(DeleteCityRequest deleteCityRequest);

	Result update(UpdateCityRequest updateCityRequest);

}

