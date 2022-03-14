package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.dtos.get.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCorporateCustomerDto;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {
	
	private CorporateCustomerDao corporateCustomerDao;
    private ModelMapperService modelMapperService;

    @Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		List<CorporateCustomer> result = corporateCustomerDao.findAll();
        List<ListCorporateCustomerDto> response = result.stream().map(corporateCustomer -> modelMapperService.forDto().map(corporateCustomer,ListCorporateCustomerDto.class)).collect(Collectors.toList());
        
        return new SuccessDataResult<List<ListCorporateCustomerDto>>(response);
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(int id) throws BusinessException {
		
		CorporateCustomer corporateCustomer = corporateCustomerDao.getById(id);
			if (corporateCustomer == null) {
				return new ErrorDataResult<GetCorporateCustomerDto>("Corporate Customer with this ID was not found!");
			}
		checkIfCorporateCustomerIdExists(corporateCustomer.getId());
		GetCorporateCustomerDto response = modelMapperService.forDto().map(corporateCustomer, GetCorporateCustomerDto.class);
	
		return new SuccessDataResult<GetCorporateCustomerDto>(response, "Success");
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Added : " + corporateCustomer.getFirstName() + corporateCustomer.getLastName());
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer corporateCustomer = corporateCustomerDao.getById(deleteCorporateCustomerRequest.getUserId());
		checkIfCorporateCustomerIdExists(corporateCustomer.getId());
		this.corporateCustomerDao.delete(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Deleted : " + corporateCustomer.getFirstName() + corporateCustomer.getLastName());
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer corporateCustomer = corporateCustomerDao.getById(updateCorporateCustomerRequest.getUserId());
		checkIfCorporateCustomerIdExists(corporateCustomer.getId());
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Updated : " + corporateCustomer.getFirstName() + corporateCustomer.getLastName());
	}


	private void checkIfCorporateCustomerIdExists(int corporateCustomerId) throws BusinessException {
	
		if(!this.corporateCustomerDao.existsById(corporateCustomerId)) {
			throw new BusinessException("Corporate Customer with this ID was not found!");
		}
	}

}
