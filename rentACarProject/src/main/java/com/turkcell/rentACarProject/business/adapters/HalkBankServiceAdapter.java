package com.turkcell.rentACarProject.business.adapters;

import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.PosService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.outServices.HalkBankPosManager;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
@Primary
public class HalkBankServiceAdapter implements PosService {

	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {

		HalkBankPosManager halkBank = new HalkBankPosManager();

		if(halkBank != null) {
			halkBank.odemeYap(createPaymentRequest.getCardInfo());
		}
		
		return new ErrorResult(Messages.PAYMENTCARDFAIL);
	}

}
