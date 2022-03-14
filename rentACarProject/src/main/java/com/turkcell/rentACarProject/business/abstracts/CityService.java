package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCityDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCityDto;
import com.turkcell.rentACarProject.business.requests.city.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.city.DeleteCityRequest;
import com.turkcell.rentACarProject.business.requests.city.UpdateCityRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.entities.concretes.City;

public interface CityService {
	
	DataResult<List<ListCityDto>> getAll();

	DataResult<GetCityDto> getById(int id) throws BusinessException;
	
	City getByCityId(int cityId);

	Result add(CreateCityRequest createCityRequest)throws BusinessException;

	Result delete(DeleteCityRequest deleteCityRequest)throws BusinessException;

	Result update(UpdateCityRequest updateCityRequest) throws BusinessException;

}

