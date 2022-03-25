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

import com.turkcell.rentACarProject.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACarProject.business.dtos.get.GetAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalServices")
public class AdditionalServicesController {

	private AdditionalServiceService additionalServiceService;

	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		
		this.additionalServiceService = additionalServiceService;
	}
	
	@GetMapping("/get/all")
	public DataResult<List<GetAdditionalServiceDto>> getAll() {
		
		return additionalServiceService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetAdditionalServiceDto> getById(@RequestParam long id) {
		
		return additionalServiceService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createRequest) {
		
		return this.additionalServiceService.add(createRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id) {
		
		return this.additionalServiceService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam long id, @RequestBody @Valid UpdateAdditionalServiceRequest updateRequest) {
		
		return this.additionalServiceService.update(id, updateRequest);
	}
	
}
