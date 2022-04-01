package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.CardInfoService;
import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.abstracts.PosService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetPaymentDto;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.Customer;
import com.turkcell.rentACarProject.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private InvoiceService invoiceService;
	private CarRentalDao carRentalDao;
	private CarRentalService carRentalService;
	private ModelMapperService modelMapperService;
	private CardInfoService cardInfoService;
	private PosService posService;
	
	@Autowired
	public PaymentManager(PaymentDao paymentDao, InvoiceService invoiceService, CarRentalDao carRentalDao, CarRentalService carRentalService, ModelMapperService modelMapperService,CardInfoService cardInfoService, PosService posService) {
		
		this.paymentDao = paymentDao;
		this.invoiceService = invoiceService;
		this.carRentalDao = carRentalDao;
		this.carRentalService = carRentalService;
		this.modelMapperService = modelMapperService;
		this.cardInfoService = cardInfoService;
		this.posService = posService;
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
		
		Customer customer = new Customer();
        customer.setUserId(carRentalService.getById(payment.getCarRental().getId()).getData().getCustomerId());
        payment.setCustomer(customer);
        
        saveCardInfo(createPaymentRequest.getCardInfo(), rememberCardInfo, carRentalService.getById(payment.getCarRental().getId()).getData().getCustomerId());

        payment = this.paymentDao.saveAndFlush(payment);

		invoiceService.add(new CreateInvoiceRequest(payment.getId()));
		sendPosService(createPaymentRequest);

		return new SuccessResult(Messages.PAYMENTADD);
	}
	
	@Override
    public void addForExtra(CreatePaymentRequest createPaymentRequest, boolean rememberCardInfo, double newExtraTotal) {
        
		checkCarRentalIdExists(createPaymentRequest.getCarRentalId());

        Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);

        payment.setTotalPayment(newExtraTotal);

        Customer customer = new Customer();
        customer.setUserId(carRentalService.getById(payment.getCarRental().getId()).getData().getCustomerId());
        payment.setCustomer(customer);

        saveCardInfo(createPaymentRequest.getCardInfo(), rememberCardInfo, carRentalService.getById(payment.getCarRental().getId()).getData().getCustomerId());

        payment = this.paymentDao.saveAndFlush(payment);

        invoiceService.add(new CreateInvoiceRequest(payment.getId()));

        sendPosService(createPaymentRequest);

        new SuccessResult(Messages.PAYMENTADD);
    }
	
	private void sendPosService(CreatePaymentRequest createPaymentRequest) {
       
		this.posService.payment(createPaymentRequest);
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
	
	
	private void saveCardInfo(CreateCardInfoRequest createCardInfoRequest, boolean rememberCardInfo, long customerId) {
		
		if (rememberCardInfo) {
			
            this.cardInfoService.add(createCardInfoRequest, customerId);

            new SuccessResult(Messages.CREDITCARDSAVE);
        }
        new ErrorResult(Messages.CREDITCARDNOTSAVE);
    }

}
