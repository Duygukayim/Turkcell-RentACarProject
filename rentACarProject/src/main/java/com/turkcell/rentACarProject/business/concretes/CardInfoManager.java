package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CardInfoService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCardInfoDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCardInfoDto;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.cardInfo.DeleteCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.cardInfo.UpdateCardInfoRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CardInfoDao;
import com.turkcell.rentACarProject.entities.concretes.CardInfo;

@Service
public class CardInfoManager implements CardInfoService {
	
	private CardInfoDao cardInfoDao;
	private ModelMapperService modelMapperService;

	public CardInfoManager(CardInfoDao cardInfoDao, ModelMapperService modelMapperService) {
		this.cardInfoDao = cardInfoDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCardInfoDto>> getAll() {
		
		List<CardInfo> result = cardInfoDao.findAll();
		List<ListCardInfoDto> response = result.stream().map(car -> modelMapperService.forDto().map(car, ListCardInfoDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCardInfoDto>>(response, Messages.CREDITCARDLIST);
	}

	@Override
	public DataResult<GetCardInfoDto> getById(int id) {
		
		CardInfo cardInfo = cardInfoDao.getById(id);
		checkCardInfoIdExists(cardInfo.getId());
		GetCardInfoDto response = modelMapperService.forDto().map(cardInfo, GetCardInfoDto.class);
		
		return new SuccessDataResult<GetCardInfoDto>(response, Messages.CREDITCARDFOUND);
	}

	@Override
	public Result add(CreateCardInfoRequest createCardInfoRequest) {
		
		checkIfCardHolderNameExists(createCardInfoRequest.getCardHolderName());
		
		CardInfo cardInfo = this.modelMapperService.forRequest().map(createCardInfoRequest, CardInfo.class);
		this.cardInfoDao.save(cardInfo);
		
		return new SuccessResult(Messages.CREDITCARDADD);
	}

	@Override
	public Result delete(DeleteCardInfoRequest deleteCardInfoRequest) {
		
		checkCardInfoIdExists(deleteCardInfoRequest.getId());
		
		CardInfo cardInfo = this.modelMapperService.forRequest().map(deleteCardInfoRequest, CardInfo.class);
		this.cardInfoDao.delete(cardInfo);
		
		return new SuccessResult(Messages.CREDITCARDELETE);
	}

	@Override
	public Result update(UpdateCardInfoRequest updateCardInfoRequest) {
		
		checkCardInfoIdExists(updateCardInfoRequest.getId());
		
		CardInfo cardInfo = this.modelMapperService.forRequest().map(updateCardInfoRequest, CardInfo.class);
		this.cardInfoDao.save(cardInfo);
		
		return new SuccessResult(Messages.CREDITCARDUPDATE);
	}
	
	private void checkCardInfoIdExists(int cardInfoId) {
		
		if(!this.cardInfoDao.existsById(cardInfoId)) {
			throw new BusinessException(Messages.CREDITCARDNOTFOUND);
		}
	}
	
	private boolean checkIfCardHolderNameExists(String cardHolderName) throws BusinessException{
		
		CardInfo cardInfo = this.cardInfoDao.getCardInfoByCardHolderName(cardHolderName);
		if (cardInfo == null) {
			return true;
		}
		throw new BusinessException(Messages.CREDITCARDALREADYEXISTS);	
	}

}