package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.dtos.get.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.dtos.list.ListIndividualCustomerDto;
import com.turkcell.rentACarProject.business.requests.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomer.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;


@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	
	private IndividualCustomerDao individualCustomerDao;
    private ModelMapperService modelMapperService;

    @Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		List<IndividualCustomer> result = individualCustomerDao.findAll();
        List<ListIndividualCustomerDto> response = result.stream().map(individualCustomer -> modelMapperService.forDto().map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
        
        return new SuccessDataResult<List<ListIndividualCustomerDto>>(response);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) throws BusinessException {
		
		IndividualCustomer individualCustomer = individualCustomerDao.getById(id);
		
			if (individualCustomer == null) {
				return new ErrorDataResult<GetIndividualCustomerDto>("Individual Customer with this ID was not found!");
			}
		checkIfIndividualCustomerIdExists(individualCustomer.getId());
		GetIndividualCustomerDto response = modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

		return new SuccessDataResult<GetIndividualCustomerDto>(response, "Success");
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Added : " + individualCustomer.getFirstName() + individualCustomer.getLastName());
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
		IndividualCustomer individualCustomer = individualCustomerDao.getById(deleteIndividualCustomerRequest.getUserId());
		checkIfIndividualCustomerIdExists(individualCustomer.getId());
		this.individualCustomerDao.delete(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Deleted : " + individualCustomer.getFirstName() + individualCustomer.getLastName());
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
		IndividualCustomer individualCustomer = individualCustomerDao.getById(updateIndividualCustomerRequest.getUserId());

		checkIfIndividualCustomerIdExists(individualCustomer.getId());
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Updated : " + individualCustomer.getFirstName() + individualCustomer.getLastName());
	}
	

	private void checkIfIndividualCustomerIdExists(int individualCustomerId) throws BusinessException {
	
		if(!this.individualCustomerDao.existsById(individualCustomerId)) {
			throw new BusinessException("Individual Customer with this ID was not found!");
		}
	}

}
