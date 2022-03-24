package com.turkcell.rentACarProject.business.adapters;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.PosService;
import com.turkcell.rentACarProject.business.outServices.IsBankPosManager;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
@Primary
public class IsBankPosAdapter implements PosService {

	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {
		IsBankPosManager isBankPosManager = new IsBankPosManager();
		return isBankPosManager.makePayment(createPaymentRequest.getCardHolderName(), createPaymentRequest.getCardNumber(), createPaymentRequest.getCardCvvNumber());
	}

}
