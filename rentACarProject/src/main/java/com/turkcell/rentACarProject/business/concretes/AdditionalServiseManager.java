package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACarProject.entities.concretes.AdditionalService;

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
	public DataResult<List<GetAdditionalServiceDto>> getAll() {
		
		List<AdditionalService> result = additionalServiceDao.findAll();
		List<GetAdditionalServiceDto> response = result.stream().map(additionalService -> modelMapperService.forDto().map(additionalService, GetAdditionalServiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetAdditionalServiceDto>>(response, Messages.ADDITIONALSERVICELIST);
	}
	

	@Override
	public DataResult<GetAdditionalServiceDto> getById(long id) {
		
		checkIfAdditionalServiceIdExists(id);
		
		AdditionalService additionalService = additionalServiceDao.getById(id);
		GetAdditionalServiceDto response = modelMapperService.forDto().map(additionalService, GetAdditionalServiceDto.class);
		
		return new SuccessDataResult<GetAdditionalServiceDto>(response, Messages.ADDITIONALSERVICEFOUND);
	}
	

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		
		checkIfAdditionalServiceNameExists(createAdditionalServiceRequest.getName());
		checkIfCarDailyPriceLessThanZero(createAdditionalServiceRequest.getDailyPrice());
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult(Messages.ADDITIONALSERVICEADD);
	}
	

	@Override
	public Result delete(long id) {
		
		checkIfAdditionalServiceIdExists(id);
		
		this.additionalServiceDao.deleteById(id);
			
		return new SuccessResult(Messages.ADDITIONALSERVICEDELETE);
	}
	

	@Override
	public Result update(long id, UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		
		checkIfAdditionalServiceIdExists(id);
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
		additionalService.setId(id);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult(Messages.ADDITIONALSERVICEUPDATE);
	}
	
	private void checkIfAdditionalServiceIdExists(long additionalServiceId) throws BusinessException {
		
		if(!this.additionalServiceDao.existsById(additionalServiceId)) {
			throw new BusinessException(Messages.ADDITIONALSERVICENOTFOUND);
		}
	}

	private boolean checkIfAdditionalServiceNameExists(String additionalServiceName) throws BusinessException {
		
		AdditionalService additionalService = this.additionalServiceDao.findByName(additionalServiceName);
		if (additionalService == null) {
			return true;
		}
		throw new BusinessException(Messages.ADDITIONALSERVICEALREADYEXISTS);
	}

	private void checkIfCarDailyPriceLessThanZero(double dailyPrice) throws BusinessException {
		
		if (dailyPrice <= 0) {
			throw new BusinessException(Messages.ADDITIONALSERVICEDAILYPRICEERROR);
		}
	}

}
