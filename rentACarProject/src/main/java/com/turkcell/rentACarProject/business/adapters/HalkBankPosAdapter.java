package com.turkcell.rentACarProject.business.adapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.PosService;
import com.turkcell.rentACarProject.business.outServices.HalkBankPosManager;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
@Primary
public class HalkBankPosAdapter implements PosService {

	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {
		HalkBankPosManager halkBankPosManager = new HalkBankPosManager();
		return halkBankPosManager.odemeYap( createPaymentRequest.getCardNumber(), createPaymentRequest.getCardHolderName(), createPaymentRequest.getCardCvvNumber());
	}
	
}

