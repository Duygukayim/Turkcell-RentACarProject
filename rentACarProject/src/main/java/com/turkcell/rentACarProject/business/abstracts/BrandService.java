package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.dtos.ListBrandDto;
import com.turkcell.rentACarProject.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface BrandService {

	DataResult<List<ListBrandDto>> getAll();

	DataResult<GetBrandDto> getById(int id) throws BusinessException;

	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

	Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;

	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

}
