package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;


@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	
	private final IndividualCustomerDao individualCustomerDao;
    private final ModelMapperService modelMapperService;

    @Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
		
    	this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<GetIndividualCustomerDto>> getAll() {
		
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
        List<GetIndividualCustomerDto> response = result.stream().map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class)).collect(Collectors.toList());
        
        return new SuccessDataResult<>(response, Messages.CUSTOMERLIST);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(long id) {
		
		checkUserIdExist(id);
		
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
		GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

		return new SuccessDataResult<>(response, Messages.CUSTOMERFOUND);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		
		checkEmailExist(createIndividualCustomerRequest.getEmail());
		checkIdentityNumberExist(createIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result delete(long id) {
		
		checkUserIdExist(id);
		
		this.individualCustomerDao.deleteById(id);
		
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public Result update(long id, UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		
		checkUserIdExist(id);
		checkEmailExist(updateIndividualCustomerRequest.getEmail());
		checkIdentityNumberExist(updateIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		individualCustomer.setUserId(id);
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}
	

	private void checkUserIdExist(long userId) {
        if (!Objects.nonNull(individualCustomerDao.findByUserId(userId)))
            throw new BusinessException(Messages.CUSTOMERNOTFOUND);
    }

    private void checkEmailExist(String email) {
        if (Objects.nonNull(individualCustomerDao.findByEmail(email)))
            throw new BusinessException(Messages.CUSTOMERISALREADYEXISTS);
    }

    private void checkIdentityNumberExist(String identityNumber) {
        if (Objects.nonNull(individualCustomerDao.findByIdentityNumber(identityNumber)))
            throw new BusinessException(Messages.CUSTOMERIDENTITYNUMBEREXISTS);
    }


}
