package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACarProject.business.dtos.get.GetAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
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
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		List<AdditionalService> result = additionalServiceDao.findAll();
        if (result.isEmpty()) {
            return new ErrorDataResult<List<ListAdditionalServiceDto>>("AdditionalServices.NotListed");
        }
		List<ListAdditionalServiceDto> response = result.stream().map(
				additionalService -> modelMapperService.forDto().map(additionalService, ListAdditionalServiceDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int id) {
		AdditionalService additionalService = additionalServiceDao.getById(id);
		if (!checkIfAdditionalServiceId(additionalService.getId())) {
			return new ErrorDataResult<GetAdditionalServiceDto>("AdditionalService.NotFound , Additional Service with this ID was not found!");
		}
		GetAdditionalServiceDto response = modelMapperService.forDto().map(additionalService,
				GetAdditionalServiceDto.class);
		return new SuccessDataResult<GetAdditionalServiceDto>(response, "Success");
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,
				AdditionalService.class);
		if (checkIfAdditionalServiceName(additionalService.getName())) {
			return new ErrorResult("AdditionalService.NotAdded : " + additionalService.getName() + " , Additional Service already exists!");
		}
		if(!checkIfCarDailyPriceLessThanZero(additionalService.getDailyPrice())) {
			return new ErrorResult("AdditionalService.NotAdded : " + additionalService.getName() + " , Daily price cannot be less than or equal to 0.");
		}
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult("AdditionalService.Added : " + additionalService.getName());
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(deleteAdditionalServiceRequest,
				AdditionalService.class);
		if (checkIfAdditionalServiceId(additionalService.getId())) {
			this.additionalServiceDao.deleteById(additionalService.getId());
			return new SuccessResult("AdditionalService.Deleted : " + additionalService.getName());
		}
		return new ErrorResult("AdditionalService.NotDeleted : " + additionalService.getName() + " , Additional Service with given ID not exists!");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,
				AdditionalService.class);
		if (checkIfAdditionalServiceId(additionalService.getId())) {
			this.additionalServiceDao.save(additionalService);
			return new SuccessResult("AdditionalService.Updated : " + additionalService.getName());
		}
		return new ErrorResult("AdditionalService.NotUpdated : " + additionalService.getName() + " , Additional Service with given ID not exists!");
	}

	private boolean checkIfAdditionalServiceId(int additionalServiceId) {
		return Objects.nonNull(additionalServiceDao.getAdditionalServiceById(additionalServiceId));
	}

	private boolean checkIfAdditionalServiceName(String additionalServiceName) {
		return Objects.nonNull(additionalServiceDao.getAdditionalServiceByName(additionalServiceName));
	}

	private boolean checkIfCarDailyPriceLessThanZero(double dailyPrice) {
		if (dailyPrice <= 0) {
			return true;
		}
		return false;
	}

}
