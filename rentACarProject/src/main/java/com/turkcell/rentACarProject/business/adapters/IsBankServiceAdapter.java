package com.turkcell.rentACarProject.business.adapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.PosService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.outServices.IsBankPosManager;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service

public class IsBankServiceAdapter implements PosService {

	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {
		
		IsBankPosManager isBank = new IsBankPosManager();
		
		if(isBank != null) {
			isBank.makePayment(createPaymentRequest.getCardInfo());
		}
		
		return new ErrorResult(Messages.PAYMENTCARDFAIL);
	}

}
