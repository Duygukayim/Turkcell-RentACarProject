package com.turkcell.rentACarProject.business.abstracts;


import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetPaymentDto;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface PaymentService {
	
	DataResult<List<GetPaymentDto>> getAll();

	DataResult<GetPaymentDto> getById(long id);
	
	Result add(CreatePaymentRequest createRequest, boolean rememberCardInfo );

}
