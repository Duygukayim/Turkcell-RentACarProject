package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.constants.Messages;
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
import com.turkcell.rentACarProject.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;


@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	
	private IndividualCustomerDao individualCustomerDao;
	private CustomerDao customerDao;
    private ModelMapperService modelMapperService;

    @Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, CustomerDao customerDao, ModelMapperService modelMapperService) {
		this.individualCustomerDao = individualCustomerDao;
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
        List<ListIndividualCustomerDto> response = result.stream().map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
        
        return new SuccessDataResult<List<ListIndividualCustomerDto>>(response, Messages.CUSTOMERLIST);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) {
		
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
		checkIfIndividualCustomerIdExists(individualCustomer.getCustomerId());
		GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

		return new SuccessDataResult<GetIndividualCustomerDto>(response, Messages.CUSTOMERFOUND);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		checkIfEmailExists(individualCustomer.getEmail());
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		
		IndividualCustomer individualCustomer = individualCustomerDao.getById(deleteIndividualCustomerRequest.getCustomerId());
		checkIfIndividualCustomerIdExists(individualCustomer.getCustomerId());
		this.individualCustomerDao.delete(individualCustomer);
		
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		
		IndividualCustomer individualCustomer = individualCustomerDao.getById(updateIndividualCustomerRequest.getCustomerId());
		checkIfIndividualCustomerIdExists(individualCustomer.getCustomerId());
		checkIfEmailExists(individualCustomer.getEmail());
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}
	

	private void checkIfIndividualCustomerIdExists(int individualCustomerId) {
	
		if(!this.individualCustomerDao.existsById(individualCustomerId)) {
			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}
	}
	
	
	private boolean checkIfEmailExists(String email) {
		
		if (this.customerDao.getCustomerByEmail(email) == null) {	
			return true;
		}
		throw new BusinessException(Messages.USEREMAILALREADYEXISTS);
	}


}
