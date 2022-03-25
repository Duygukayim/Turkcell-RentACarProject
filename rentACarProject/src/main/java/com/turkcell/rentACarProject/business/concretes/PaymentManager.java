package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.CardInfoService;
import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetPaymentDto;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.CardInfo;
import com.turkcell.rentACarProject.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private InvoiceService invoiceService;
	private CarRentalDao carRentalDao;
	private CarRentalService carRentalService;
	private ModelMapperService modelMapperService;
	private CardInfoService cardInfoService;
	
	@Autowired
	public PaymentManager(PaymentDao paymentDao, InvoiceService invoiceService, CarRentalDao carRentalDao, CarRentalService carRentalService, ModelMapperService modelMapperService,CardInfoService cardInfoService) {
		this.paymentDao = paymentDao;
		this.invoiceService = invoiceService;
		this.carRentalDao = carRentalDao;
		this.carRentalService = carRentalService;
		this.modelMapperService = modelMapperService;
		this.cardInfoService = cardInfoService;
	}

	
	@Override
	public DataResult<List<GetPaymentDto>> getAll() {
		
		List<Payment> result = paymentDao.findAll();
		List<GetPaymentDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, GetPaymentDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetPaymentDto>>(response, Messages.PAYMENTLIST);
	}
	
	
	@Override
	public DataResult<GetPaymentDto> getById(long id) {
		
		checkPaymentIdExists(id);
		
		Payment payment = this.paymentDao.getById(id);
		GetPaymentDto response = this.modelMapperService.forDto().map(payment, GetPaymentDto.class);
		
		return new SuccessDataResult<GetPaymentDto>(response, Messages.PAYMENTFOUND);	
	}
	
	
	@Override
	public Result add(CreatePaymentRequest createPaymentRequest, boolean rememberCardInfo) {
		
		checkCarRentalIdExists(createPaymentRequest.getCarRentalId());

		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setTotalPayment(carRentalService.calTotalPrice(createPaymentRequest.getCarRentalId()));
        payment.setCardInfo(handleCardInfo(createPaymentRequest.getCardInfo(), rememberCardInfo));

		this.paymentDao.save(payment);
		invoiceService.add(new CreateInvoiceRequest(payment.getId()));

		return new SuccessResult(Messages.PAYMENTADD);
	}

	
	private void checkPaymentIdExists(long paymentId) {

		if (!this.paymentDao.existsById(paymentId)) {

			throw new BusinessException(Messages.PAYMENTNOTFOUND);
		}
	}

	
	private void checkCarRentalIdExists(long carRentalId) {

		if (!this.carRentalDao.existsById(carRentalId)) {

			throw new BusinessException(Messages.CARRENTALNOTFOUND);
		}
	}
	
	
	private CardInfo handleCardInfo(CreateCardInfoRequest createCardInfoRequest, boolean rememberCardInfo) {
        
		if (rememberCardInfo) {
            return saveCardInfo(createCardInfoRequest);
        }
        return setCardInfo(createCardInfoRequest);
    }
	
	
	private CardInfo saveCardInfo(CreateCardInfoRequest createCardInfoRequest) {
		
        return this.cardInfoService.addByPayment(createCardInfoRequest).getData();
    }
	
	
	 private CardInfo setCardInfo(CreateCardInfoRequest createCardInfoRequest) {
		 
	        CardInfo cardInfo = new CardInfo();
	        
	        cardInfo.setCardNumber(createCardInfoRequest.getCardNumber());
	        cardInfo.setCardHolderName(createCardInfoRequest.getCardHolderName());
	        cardInfo.setExpiryDate(createCardInfoRequest.getExpiryDate());
	        cardInfo.setCardCvvNumber(createCardInfoRequest.getCardCvvNumber());

	        return cardInfo;
	    }

}
