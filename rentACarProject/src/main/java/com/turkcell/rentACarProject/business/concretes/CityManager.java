package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.dtos.get.GetCityDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCityDto;
import com.turkcell.rentACarProject.business.requests.city.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.city.DeleteCityRequest;
import com.turkcell.rentACarProject.business.requests.city.UpdateCityRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CityDao;
import com.turkcell.rentACarProject.entities.concretes.City;

@Service
public class CityManager implements CityService {
	
	private CityDao cityDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCityDto>> getAll() {
		
		List<City> result = cityDao.findAll();
		List<ListCityDto> response = result.stream().map(city -> modelMapperService.forDto().map(city, ListCityDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCityDto>>(response, "Success");
	}

	@Override
	public DataResult<GetCityDto> getById(int id) throws BusinessException {
		
		City city = cityDao.getById(id);
		checkIfCityIdExists(city.getId());
		GetCityDto response = modelMapperService.forDto().map(city, GetCityDto.class);
		
		return new SuccessDataResult<GetCityDto>(response, "Success");
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) throws BusinessException {
		
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		checkIfCityNameExists(city.getName());
		this.cityDao.save(city);
		
		return new SuccessResult("City.Added : " + city.getName());
	}
	

	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException {
		
		City city = this.modelMapperService.forRequest().map(deleteCityRequest, City.class);
		checkIfCityIdExists(city.getId());
		this.cityDao.delete(city);
			
		return new SuccessResult("city.Deleted : " + city.getName());	
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {
		
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		checkIfCityIdExists(city.getId());
		this.cityDao.save(city);
		
		return new SuccessResult("City.Updated : " + city.getName());
	}
	
	
	@Override
	public City getByCityId(int cityId) {
		
		return this.cityDao.getById(cityId);
	}
	
	private void checkIfCityIdExists(int cityId) throws BusinessException {
		
		if(!this.cityDao.existsById(cityId)) {
			throw new BusinessException("A city with this ID was not found!");
		}
	}
	
	private boolean checkIfCityNameExists(String cityName) throws BusinessException{
		
		City city = this.cityDao.getCityByName(cityName);
		if (city == null) {
			return true;
		}
		throw new BusinessException("City already exists!");
		
	}

}
