package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.FindBrandDto;
import com.turkcell.rentACarProject.business.dtos.ListBrandDto;
import com.turkcell.rentACarProject.business.requests.CreateBrandRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;

public interface BrandService {

	List<ListBrandDto> listAll();

	void create(CreateBrandRequest createBrandRequest) throws BusinessException;

	FindBrandDto findById(int brandId) throws BusinessException;

}
