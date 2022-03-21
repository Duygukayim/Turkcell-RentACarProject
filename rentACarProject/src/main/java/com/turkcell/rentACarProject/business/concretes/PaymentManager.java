package com.turkcell.rentACarProject.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {
		
		//business kod eklenebilir
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		this.paymentDao.save(payment);
		
		return new SuccessResult("Payment has been made successfully.");
	}
	
	
}
