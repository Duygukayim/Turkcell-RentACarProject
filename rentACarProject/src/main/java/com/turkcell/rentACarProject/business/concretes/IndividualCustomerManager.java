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
		
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
        List<ListIndividualCustomerDto> response = result.stream().map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
        
        return new SuccessDataResult<List<ListIndividualCustomerDto>>(response);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) throws BusinessException {
		
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
		checkIfIndividualCustomerIdExists(individualCustomer.getUserId());
		GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

		return new SuccessDataResult<GetIndividualCustomerDto>(response, "Success");
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Added : " + individualCustomer.getFirstName() + " " + individualCustomer.getLastName());
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
		
		IndividualCustomer individualCustomer = individualCustomerDao.getById(deleteIndividualCustomerRequest.getUserId());
		checkIfIndividualCustomerIdExists(individualCustomer.getUserId());
		this.individualCustomerDao.delete(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Deleted : " + individualCustomer.getFirstName() + " " + individualCustomer.getLastName());
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
		
		IndividualCustomer individualCustomer = individualCustomerDao.getById(updateIndividualCustomerRequest.getUserId());
		checkIfIndividualCustomerIdExists(individualCustomer.getUserId());
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult("IndividualCustomer.Updated : " + individualCustomer.getFirstName() + " " + individualCustomer.getLastName());
	}
	

	private void checkIfIndividualCustomerIdExists(int individualCustomerId) throws BusinessException {
	
		if(!this.individualCustomerDao.existsById(individualCustomerId)) {
			throw new BusinessException("Individual Customer with this ID was not found!");
		}
	}

	@Override
	public DataResult<IndividualCustomer> getIndividualCustomerByIndividualCustomerId(int userId) {
		return new SuccessDataResult<IndividualCustomer>(this.individualCustomerDao.getById(userId));
	}
}
