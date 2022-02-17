package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.FindBrandDto;
import com.turkcell.rentACarProject.business.dtos.ListBrandDto;
import com.turkcell.rentACarProject.business.requests.CreateBrandRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;

@RestController
@RequestMapping("/api/brands")

public class BrandsController {

	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@GetMapping("/listall")
	public List<ListBrandDto> listAll() {
		return this.brandService.listAll();
	}

	@PostMapping("/create")
	public void add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {
		this.brandService.create(createBrandRequest);
	}

	@GetMapping("/find")
	public FindBrandDto find(@RequestParam int brandId) throws BusinessException {
		return this.brandService.findById(brandId);
	}
	
}