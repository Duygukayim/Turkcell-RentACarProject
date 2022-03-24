package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCorporateCustomerDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {
	
	private CorporateCustomerDao corporateCustomerDao;
	private CustomerDao customerDao;
    private ModelMapperService modelMapperService;

    @Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, CustomerDao customerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.customerDao = customerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		List<CorporateCustomer> result = corporateCustomerDao.findAll();
        List<ListCorporateCustomerDto> response = result.stream().map(corporateCustomer -> modelMapperService.forDto().map(corporateCustomer,ListCorporateCustomerDto.class)).collect(Collectors.toList());
        
        return new SuccessDataResult<List<ListCorporateCustomerDto>>(response, Messages.CUSTOMERLIST);
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(int id) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);
		checkIfCorporateCustomerIdExists(corporateCustomer.getCustomerId());
		GetCorporateCustomerDto response = this.modelMapperService.forDto().map(corporateCustomer, GetCorporateCustomerDto.class);
	
		return new SuccessDataResult<GetCorporateCustomerDto>(response, Messages.CUSTOMERFOUND);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,CorporateCustomer.class);
		checkIfEmailExists(corporateCustomer.getEmail());
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer corporateCustomer = corporateCustomerDao.getById(deleteCorporateCustomerRequest.getCustomerId());
		checkIfCorporateCustomerIdExists(corporateCustomer.getCustomerId());
		this.corporateCustomerDao.delete(corporateCustomer);
		
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer corporateCustomer = corporateCustomerDao.getById(updateCorporateCustomerRequest.getCustomerId());
		checkIfCorporateCustomerIdExists(corporateCustomer.getCustomerId());
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}


	private void checkIfCorporateCustomerIdExists(int corporateCustomerId) throws BusinessException {
	
		if(!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}
	}
	
	private boolean checkIfEmailExists(String email) {
		
		if (this.customerDao.findByEmail(email) == null) {	
			return true;
		}
		throw new BusinessException(Messages.USEREMAILALREADYEXISTS);
	}

}
