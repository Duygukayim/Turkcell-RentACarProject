package com.turkcell.rentACarProject.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.CardInfoDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private CardInfoDao cardInfoDao;
	private CarRentalDao carRentalDao;
	private ModelMapperService modelMapperService;

	public PaymentManager(PaymentDao paymentDao, CarRentalDao carRentalDao, ModelMapperService modelMapperService) {
		this.paymentDao = paymentDao;
		this.carRentalDao = carRentalDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

		checkPaymentIdExists(payment.getId());
		checkCarRentalIdExists(payment.getCarRental().getId());
		checkIfCardInfoIdExists(payment.getCardInfo().getId());

		this.paymentDao.save(payment);

		return new SuccessResult(Messages.PAYMENTADD);
	}

	
	private void checkPaymentIdExists(int paymentId) {

		if (!this.paymentDao.existsById(paymentId)) {

			throw new BusinessException(Messages.PAYMENTNOTFOUND);
		}
	}

	
	private void checkCarRentalIdExists(int carRentalId) {

		if (!this.carRentalDao.existsById(carRentalId)) {

			throw new BusinessException(Messages.CARRENTALNOTFOUND);
		}
	}

	
	private void checkIfCardInfoIdExists(int cardInfoId) { 

		if (!this.cardInfoDao.existsById(cardInfoId)) {

			throw new BusinessException(Messages.CREDITCARDNOTFOUND);
		}
	}
	

}
