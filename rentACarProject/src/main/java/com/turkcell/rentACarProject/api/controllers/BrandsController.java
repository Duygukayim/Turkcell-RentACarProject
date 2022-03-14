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

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.get.GetBrandDto;
import com.turkcell.rentACarProject.business.dtos.list.ListBrandDto;
import com.turkcell.rentACarProject.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")

public class BrandsController {

	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@GetMapping("/getAll")
	public DataResult<List<ListBrandDto>> getAll() {
		
		return brandService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetBrandDto> get(@RequestParam int id) throws BusinessException {
		
		return brandService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BusinessException {
		
		return brandService.add(createBrandRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		
		return brandService.delete(deleteBrandRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) throws BusinessException {
		
		return brandService.update(updateBrandRequest);
	}

}