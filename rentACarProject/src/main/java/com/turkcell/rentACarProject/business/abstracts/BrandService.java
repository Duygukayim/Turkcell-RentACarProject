package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.dtos.ListBrandDto;
import com.turkcell.rentACarProject.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;

public interface BrandService {

	List<ListBrandDto> getAll();
	GetBrandDto getById(int id) throws BusinessException;
	
	void create(CreateBrandRequest createBrandRequest) throws BusinessException;
	void delete(DeleteBrandRequest deleteBrandRequest);
	void update(UpdateBrandRequest updateBrandRequest);



}
