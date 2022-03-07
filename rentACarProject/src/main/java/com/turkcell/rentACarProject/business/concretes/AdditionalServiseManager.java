package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACarProject.business.dtos.GetAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.ListAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.ListCarDto;
import com.turkcell.rentACarProject.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACarProject.entities.concretes.AdditionalService;
import com.turkcell.rentACarProject.entities.concretes.Car;

@Service
public class AdditionalServiseManager implements AdditionalServiceService {
	
	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiseManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		List<AdditionalService> result = additionalServiceDao.findAll();
		List<ListAdditionalServiceDto> response = result.stream().map(additionalService -> modelMapperService.forDto().map(additionalService, ListAdditionalServiceDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int id) {
		AdditionalService additionalService = additionalServiceDao.getById(id);
		if (additionalService != null) {
			GetAdditionalServiceDto response = modelMapperService.forDto().map(additionalService, GetAdditionalServiceDto.class);
			return new SuccessDataResult<GetAdditionalServiceDto>(response, "Success");
		}
		return new ErrorDataResult<GetAdditionalServiceDto>("AdditionalService.NotFound");
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult("Additional.Added");
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(deleteAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.deleteById(additionalService.getId());
		return new SuccessResult("Additional.Deleted");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult("Additional.Updated");
	}
	
	

}
