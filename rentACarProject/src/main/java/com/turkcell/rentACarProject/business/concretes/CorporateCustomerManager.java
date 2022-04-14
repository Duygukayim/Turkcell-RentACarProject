package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {
	
	private final CorporateCustomerDao corporateCustomerDao;
    private final ModelMapperService modelMapperService;

    @Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		
    	this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<GetCorporateCustomerDto>> getAll() {
		
		List<CorporateCustomer> result = corporateCustomerDao.findAll();
        List<GetCorporateCustomerDto> response = result.stream().map(corporateCustomer -> modelMapperService.forDto().map(corporateCustomer,GetCorporateCustomerDto.class)).collect(Collectors.toList());
        
        return new SuccessDataResult<>(response, Messages.CUSTOMERLIST);
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(long id) {
		
		checkUserIdExist(id);
		
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);
		GetCorporateCustomerDto response = this.modelMapperService.forDto().map(corporateCustomer, GetCorporateCustomerDto.class);
	
		return new SuccessDataResult<>(response, Messages.CUSTOMERFOUND);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		
		checkEmailExist(createCorporateCustomerRequest.getEmail());
        checkTaxNumberExist(createCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result delete(long id) {
		
		checkUserIdExist(id);
		
		this.corporateCustomerDao.deleteById(id);
		
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public Result update(long id, UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		checkUserIdExist(id);
		checkEmailExist(updateCorporateCustomerRequest.getEmail());
        checkTaxNumberExist(updateCorporateCustomerRequest.getTaxNumber());
		
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomer.setUserId(id);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}


	private void checkUserIdExist(long userId) {
        if (!Objects.nonNull(corporateCustomerDao.findByUserId(userId)))
            throw new BusinessException(Messages.CUSTOMERNOTFOUND);
    }
	
	private void checkEmailExist(String email) {
        if (Objects.nonNull(corporateCustomerDao.findByEmail(email)))
            throw new BusinessException(Messages.CUSTOMERISALREADYEXISTS);
    }

    private void checkTaxNumberExist(String taxNumber) {
        if (Objects.nonNull(corporateCustomerDao.findByTaxNumber(taxNumber)))
            throw new BusinessException(Messages.CUSTOMERTAXNUMBEREXISTS);
    }

}
