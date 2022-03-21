package com.turkcell.rentACarProject.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.IsBankPosService;
import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.abstracts.PosService;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;

@Service
public class PosManager implements PosService {
	
	private IsBankPosService isBankPosService;
	private PaymentService paymentService;

	public PosManager(IsBankPosService isBankPosService, PaymentService paymentService) {
		this.isBankPosService = isBankPosService;
		this.paymentService = paymentService;
	}

	@Override
	public Result payment(CreatePaymentRequest createPaymentRequest) {
	
		return new SuccessResult();
	}

}
