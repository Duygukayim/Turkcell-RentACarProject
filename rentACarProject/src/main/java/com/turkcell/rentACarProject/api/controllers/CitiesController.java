package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.CityService;
import com.turkcell.rentACarProject.business.dtos.get.GetCityDto;
import com.turkcell.rentACarProject.business.requests.city.CreateCityRequest;
import com.turkcell.rentACarProject.business.requests.city.UpdateCityRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
	
	private final CityService cityService;

	@Autowired
	public CitiesController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/get/all")
	public DataResult<List<GetCityDto>> getAll() {
		
		return cityService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCityDto> get(@RequestParam long id) {
		
		return cityService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCityRequest createRequest)  {
		
		return cityService.add(createRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id) {
		
		return cityService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam long id, @RequestBody @Valid UpdateCityRequest updateRequest) {
		
		return cityService.update(id, updateRequest);
	}

}
