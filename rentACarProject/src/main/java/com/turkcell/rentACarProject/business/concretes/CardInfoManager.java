package com.turkcell.rentACarProject.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CardInfoService;
import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCardInfoDto;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.cardInfo.UpdateCardInfoRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CardInfoDao;
import com.turkcell.rentACarProject.entities.concretes.CardInfo;
import com.turkcell.rentACarProject.entities.concretes.Customer;

@Service
public class CardInfoManager implements CardInfoService {
	
	private final CardInfoDao cardInfoDao;
	private final CustomerService customerService;
	private final ModelMapperService modelMapperService;

	@Autowired
	public CardInfoManager(CardInfoDao cardInfoDao, CustomerService customerService, ModelMapperService modelMapperService) {
		
		this.cardInfoDao = cardInfoDao;
		this.customerService = customerService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<GetCardInfoDto>> getAll() {
		
		List<CardInfo> result = cardInfoDao.findAll();
		List<GetCardInfoDto> response = result.stream().map(car -> modelMapperService.forDto().map(car, GetCardInfoDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetCardInfoDto>>(response, Messages.CREDITCARDLIST);
	}

	@Override
	public DataResult<GetCardInfoDto> getById(long id) {
		
		checkCardInfoIdExists(id);
		
		CardInfo cardInfo = cardInfoDao.getById(id);
		GetCardInfoDto response = modelMapperService.forDto().map(cardInfo, GetCardInfoDto.class);
		
		return new SuccessDataResult<GetCardInfoDto>(response, Messages.CREDITCARDFOUND);
	}
	
	
	@Override
	public Result add(CreateCardInfoRequest createCardInfoRequest, long customerId) {

		checkIfCustomerIdExists(customerId);
		checkCardNumber(createCardInfoRequest.getCardNumber());
		checkCardCvvNumber(createCardInfoRequest.getCardCvvNumber());
        checkExpiryDate(createCardInfoRequest.getExpiryDate());
        checkCardExists(createCardInfoRequest);
		
		CardInfo cardInfo = this.modelMapperService.forRequest().map(createCardInfoRequest, CardInfo.class);
		
		Customer customer = new Customer();
        customer.setUserId(customerId);
        cardInfo.setCustomer(customer);
        
		this.cardInfoDao.save(cardInfo);
		
		return new SuccessResult(Messages.CREDITCARDADD);
	}

	
	@Override
	public Result delete(long id) {
		
		checkCardInfoIdExists(id);
		
		this.cardInfoDao.deleteById(id);
		
		return new SuccessResult(Messages.CREDITCARDELETE);
	}

	
	@Override
	public Result update(long id, UpdateCardInfoRequest updateCardInfoRequest) {
		
		checkCardInfoIdExists(id);
		checkCardNumber(updateCardInfoRequest.getCardNumber());
		checkCardCvvNumber(updateCardInfoRequest.getCardCvvNumber());
        checkExpiryDate(updateCardInfoRequest.getExpiryDate());
		
		CardInfo cardInfo = this.modelMapperService.forRequest().map(updateCardInfoRequest, CardInfo.class);
		cardInfo.setId(id);
		
		Customer customer = new Customer();
        customer.setUserId(cardInfoDao.findById(id).getCustomer().getUserId());   
        cardInfo.setCustomer(customer);
        
		this.cardInfoDao.save(cardInfo);
		
		return new SuccessResult(Messages.CREDITCARDUPDATE);
	}
	
	private void checkIfCustomerIdExists(long customerId) {
			
		if (this.customerService.getById(customerId) == null) {
				
			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}
	}
	
	private void checkCardInfoIdExists(long cardInfoId) {
		
		if(!this.cardInfoDao.existsById(cardInfoId)) {
			
			throw new BusinessException(Messages.CREDITCARDNOTFOUND);
		}
	}	
	
	private void checkCardNumber(String cardNumber) {
       
		if (cardNumber.length() != 16) {
           
        	throw new BusinessException(Messages.CREDITCARDNUMBERERROR);
		}
    }
	
	private void checkCardCvvNumber(String cardCvvNumber) {
       
		if (cardCvvNumber.length() != 3) {
           
        	throw new BusinessException(Messages.CREDITCARDCVVERROR);
		} 
		else if (cardCvvNumber.equals("000")) {
			
			throw new BusinessException(Messages.CREDITCARDCVVERROR);
		}
    }
	
	private void checkExpiryDate(LocalDate expiryDate) {

        if (expiryDate == null) { 
           
        	throw new BusinessException(Messages.CREDITCARDDATEERROR);
        }
    }
	
	private void checkCardExists(CreateCardInfoRequest createCardInfoRequest) {
       
		if (Objects.nonNull(cardInfoDao.findByCardHolderNameAndCardNumberAndExpiryDateAndCardCvvNumber(createCardInfoRequest.getCardHolderName(), createCardInfoRequest.getCardNumber(), createCardInfoRequest.getExpiryDate(), createCardInfoRequest.getCardCvvNumber()))) {
			
			throw new BusinessException(Messages.CREDITCARDALREADYEXISTS);			
		}
    }
	

}
