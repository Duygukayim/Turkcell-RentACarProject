package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.brand.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brand.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACarProject.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private final BrandDao brandDao;
	private final ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<GetBrandDto>> getAll() {
		
		List<Brand> result = brandDao.findAll();
		List<GetBrandDto> response = result.stream().map(brand -> modelMapperService.forDto().map(brand, GetBrandDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, Messages.BRANDLIST);
	}

	@Override
	public DataResult<GetBrandDto> getById(long id) {
		
		checkIfBrandIdExists(id);
		
		Brand brand = this.brandDao.getById(id);
		GetBrandDto response = this.modelMapperService.forDto().map(brand, GetBrandDto.class);
		
		return new SuccessDataResult<>(response, Messages.BRANDFOUND);
	}


	@Override
	public Result add(CreateBrandRequest createBrandRequest) {
		
		checkIfBrandNameExists(createBrandRequest.getName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
		this.brandDao.save(brand);
		
		return new SuccessResult(Messages.BRANDADD);
	}


	@Override
	public Result delete(long id) {
		
		checkIfBrandIdExists(id);
		
		this.brandDao.deleteById(id);
		
		return new SuccessResult(Messages.BRANDDELETE);
	}

	
	@Override
	public Result update(long id, UpdateBrandRequest updateBrandRequest) {
		
		checkIfBrandIdExists(id);
		checkIfBrandNameExists(updateBrandRequest.getName());
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		brand.setId(id);
		
		this.brandDao.save(brand);
		
		return new SuccessResult(Messages.BRANDUPDATE);
		
	}

	private void checkIfBrandNameExists(String brandName) {
		
		Brand brand = this.brandDao.findByName(brandName);
		if (brand == null) {
			return;
		}
		throw new BusinessException(Messages.BRANDALREADYEXISTS);
	}

	private void checkIfBrandIdExists(long id) {
		
		if(!this.brandDao.existsById(id)) {
			throw new BusinessException(Messages.BRANDNOTFOUND);
		}
	}

}