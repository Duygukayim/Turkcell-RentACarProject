package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.get.GetBrandDto;
import com.turkcell.rentACarProject.business.dtos.list.ListBrandDto;
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
				.map(brand -> modelMapperService.forDto().map(brand, ListBrandDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListBrandDto>>(response);
	}

	@Override
	public DataResult<GetBrandDto> getById(int id) throws BusinessException {
		Brand brand = this.brandDao.getById(id);
		if (checkIfBrandId(brand.getId())) {
			GetBrandDto response = this.modelMapperService.forDto().map(brand, GetBrandDto.class);
			return new SuccessDataResult<GetBrandDto>(response, "Success");
		}
		return new ErrorDataResult<GetBrandDto>("Brand.NotFounded , A brand with this ID was not found!");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		if (!checkIfBrandName(brand.getName())) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.Added : " + brand.getName());
		}
		return new ErrorResult("Brand.NotAdded : " + brand.getName() + "Brand already exists!");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		if (checkIfBrandId(brand.getId())) {
			this.brandDao.delete(brand);
			return new SuccessResult("Brand.Deleted : " + brand.getName());
		}
		return new ErrorResult("Brand.NotDeleted : " + brand.getName() + "A brand with this ID was not found!");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		if (checkIfBrandId(brand.getId())) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.Updated : " + brand.getName());
		}
		return new ErrorResult("Brand.NotUpdated : " + brand.getName() + "A brand with this ID was not found!");
	}

	private boolean checkIfBrandName(String brandName) {
		return Objects.nonNull(brandDao.getBrandByName(brandName));
//		return new BusinessException("Brand already exists!");
	}

	private boolean checkIfBrandId(int brandId) {
		return Objects.nonNull(brandDao.getBrandById(brandId));
//		return new BusinessException("A brand with this ID was not found!");
	}

}