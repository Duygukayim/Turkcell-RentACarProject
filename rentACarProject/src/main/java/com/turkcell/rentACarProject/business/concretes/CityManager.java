package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.dtos.get.GetCityDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCityDto;
import com.turkcell.rentACarProject.business.requests.city.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.city.DeleteCityRequest;
import com.turkcell.rentACarProject.business.requests.city.UpdateCityRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
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
	public DataResult<GetCityDto> getById(int id) {
		City city = cityDao.getById(id);
		if (checkIfCityIdExists(city.getId())) {
			GetCityDto response = modelMapperService.forDto().map(city, GetCityDto.class);
			return new SuccessDataResult<GetCityDto>(response, "Success");
		}
		return new ErrorDataResult<GetCityDto>("City.NotFounded , A city with this ID was not found!");
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		if (!checkIfCityName(city.getName())) {
			this.cityDao.save(city);
			return new SuccessResult("City.Added : " + city.getName());
		}
		return new ErrorResult("City.NotAdded : " + city.getName() + " , City already exists!");
	}

	@Override
	public Result delete(DeleteCityRequest deleteCityRequest) {
		City city = this.modelMapperService.forRequest().map(deleteCityRequest, City.class);
		if (checkIfCityIdExists(city.getId())) {
			this.cityDao.delete(city);
			return new SuccessResult("city.Deleted : " + city.getName());
		}
		return new ErrorResult("city.NotDeleted : " + city.getName() + " , A city with this ID was not found!");
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		if (checkIfCityIdExists(city.getId())) {
			this.cityDao.save(city);
			return new SuccessResult("City.Updated : " + city.getName());
		}
		return new ErrorResult("City.NotUpdated : " + city.getName() + " , A city with this ID was not found!");
	}
	
	private boolean checkIfCityIdExists(int cityId) {
		return Objects.nonNull(cityDao.getCityById(cityId));
	}
	
	private boolean checkIfCityName(String cityName) {
		return Objects.nonNull(cityDao.getCityByName(cityName));
//		return new ErrorResult("Color already exists!");
	}

}
