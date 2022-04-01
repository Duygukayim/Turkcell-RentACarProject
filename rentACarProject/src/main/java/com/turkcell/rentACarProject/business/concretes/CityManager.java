package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCityDto;
import com.turkcell.rentACarProject.business.requests.city.CreateCityRequest;
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
	public DataResult<List<GetCityDto>> getAll() {
		
		List<City> result = cityDao.findAll();
		List<GetCityDto> response = result.stream().map(city -> modelMapperService.forDto().map(city, GetCityDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCityDto>>(response, Messages.CITYLIST);
	}

	@Override
	public DataResult<GetCityDto> getById(long id) {
		
		checkIfCityIdExists(id);
		
		City city = cityDao.getById(id);
		GetCityDto response = modelMapperService.forDto().map(city, GetCityDto.class);
		
		return new SuccessDataResult<GetCityDto>(response, Messages.CITYFOUND);
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {
		
		checkIfCityNameExists(createCityRequest.getName());
		
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		this.cityDao.save(city);
		
		return new SuccessResult(Messages.CITYADD);
	}
	

	@Override
	public Result delete(long id) {
		
		checkIfCityIdExists(id);
		
		this.cityDao.deleteById(id);
			
		return new SuccessResult(Messages.CITYDELETE);	
	}

	
	@Override
	public Result update(long id, UpdateCityRequest updateCityRequest) {
		
		checkIfCityIdExists(id);
		
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		city.setId(id);
		
		this.cityDao.save(city);
		
		return new SuccessResult(Messages.CITYUPDATE);
	}
	
	
	private void checkIfCityIdExists(long cityId) {
		
		if(!this.cityDao.existsById(cityId)) {
			throw new BusinessException(Messages.CITYNOTFOUND);
		}
	}
	
	private boolean checkIfCityNameExists(String cityName) {
		
		City city = this.cityDao.findByName(cityName);
		if (city == null) {
			return true;
		}
		throw new BusinessException(Messages.CITYEXISTS);
		
	}

}
