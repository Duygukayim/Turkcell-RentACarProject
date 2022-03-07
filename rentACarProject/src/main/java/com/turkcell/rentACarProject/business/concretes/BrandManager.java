package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.dtos.ListBrandDto;
import com.turkcell.rentACarProject.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACarProject.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListBrandDto>> getAll() {
		List<Brand> result = brandDao.findAll();
		List<ListBrandDto> response = result.stream()
				.map(brand -> modelMapperService.forDto().map(brand, ListBrandDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListBrandDto>>(response);
	}

	@Override
	public DataResult<GetBrandDto> getById(int id) throws BusinessException {
		Brand brand = this.brandDao.getById(id);
		if (brand != null) {
			GetBrandDto response = modelMapperService.forDto().map(brand, GetBrandDto.class);
			return new SuccessDataResult<GetBrandDto>(response, "Success");
		}
		return new ErrorDataResult<GetBrandDto>("Brand.NotFounded");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		if (checkIfBrandName(brand.getName()).isSuccess()) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.Added : " + brand.getName());
		}
		return new ErrorResult("Brand.NotAdded : " + brand.getName());
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		if (checkIfBrandId(brand.getId()).isSuccess()) {
			this.brandDao.delete(brand);
			return new SuccessResult("Brand.Deleted : " + brand.getName());
		}
		return new ErrorResult("Brand.NotDeleted : " + brand.getName());
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		if (!checkIfBrandId(brand.getId()).isSuccess()) {
			return new ErrorResult("Brand.NotUpdated : " + brand.getName());
		}
		if (!checkIfBrandName(brand.getName()).isSuccess()) {
			return new ErrorResult("Brand.NotUpdated : " + brand.getName());
		}
		this.brandDao.save(brand);
		return new SuccessResult("Brand.Updated : " + brand.getName());
	}

	private Result checkIfBrandName(String brandName) throws BusinessException {
		if (brandDao.getBrandByName(brandName) == null) {
			return new SuccessResult();
		}
		return new ErrorResult("Brand already exists!");
	}

	private Result checkIfBrandId(int brandId) throws BusinessException {
		if (this.brandDao.getBrandById(brandId) != null) {
			return new SuccessResult();
		}
		return new ErrorResult("A brand with this id was not found!");
	}
}
