package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;


public interface BrandService {

	DataResult<List<GetBrandDto>> getAll();

	DataResult<GetBrandDto> getById(long id);

	Result add(CreateBrandRequest createRequest);

	Result delete(long id);

	Result update(long id, UpdateBrandRequest updateRequest);

}
