package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.entities.concretes.Brand;
import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.FindBrandDto;
import com.turkcell.rentACarProject.business.dtos.ListBrandDto;
import com.turkcell.rentACarProject.business.requests.CreateBrandRequest;
import com.turkcell.rentACarProject.dataAccess.abstracts.BrandDao;

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
	public List<ListBrandDto> listAll() {
		var result = this.brandDao.findAll();
		List<ListBrandDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, ListBrandDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void create(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		checkIfBrandExists(brand);
		this.brandDao.save(brand);
	}

	@Override
	public FindBrandDto findById(int brandId) throws BusinessException {
		Brand result = this.brandDao.findById(brandId).get();
		FindBrandDto response = this.modelMapperService.forDto().map(result, FindBrandDto.class);
		return response;

	}

	public void checkIfBrandExists(Brand brand) throws BusinessException {
		if (this.brandDao.getAllByBrandName(brand.getBrandName()).stream().count() != 0) {
			throw new BusinessException("Brand already exists!");
		}
	}
}
