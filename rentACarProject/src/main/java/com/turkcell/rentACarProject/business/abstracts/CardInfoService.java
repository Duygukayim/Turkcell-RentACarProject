package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCardInfoDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCardInfoDto;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.cardInfo.DeleteCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.cardInfo.UpdateCardInfoRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface CardInfoService {
	
	DataResult<List<ListCardInfoDto>> getAll();

	DataResult<GetCardInfoDto> getById(int id);
	
	Result add(CreateCardInfoRequest createCardInfoRequest);

	Result delete(DeleteCardInfoRequest deleteCardInfoRequest);

	Result update(UpdateCardInfoRequest updateCardInfoRequest);

}
