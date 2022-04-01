package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetCardInfoDto;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.cardInfo.UpdateCardInfoRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface CardInfoService {
	
	DataResult<List<GetCardInfoDto>> getAll();

	DataResult<GetCardInfoDto> getById(long id);
	
	Result add(CreateCardInfoRequest createRequest, long customerId);

	Result delete(long id);

	Result update(long id, UpdateCardInfoRequest updateRequest);


}
