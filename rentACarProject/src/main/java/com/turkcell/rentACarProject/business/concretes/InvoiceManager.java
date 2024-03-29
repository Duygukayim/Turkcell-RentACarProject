package com.turkcell.rentACarProject.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.get.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.get.GetPaymentDto;
import com.turkcell.rentACarProject.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACarProject.entities.concretes.Customer;
import com.turkcell.rentACarProject.entities.concretes.Invoice;
import com.turkcell.rentACarProject.entities.concretes.Payment;

@Service
public class InvoiceManager implements InvoiceService {
	
	private final InvoiceDao invoiceDao;
	private final CarRentalService carRentalService;
	private final PaymentService paymentService;
	private final ModelMapperService modelMapperService;
	private final CustomerService customerService;

	public InvoiceManager(InvoiceDao invoiceDao, CarRentalService carRentalService, PaymentService paymentService, ModelMapperService modelMapperService, CustomerService customerService) {
		this.invoiceDao = invoiceDao;
		this.carRentalService = carRentalService;
		this.paymentService = paymentService;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public DataResult<List<GetInvoiceDto>> getAll() {
		
		List<Invoice> result = invoiceDao.findAll();
		List<GetInvoiceDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, GetInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, Messages.INVOICELIST);
	}


	@Override
	public DataResult<GetInvoiceDto> getById(long id) {
		
		checkIfInvoiceIdExists(id);

		Invoice invoice = this.invoiceDao.getById(id);
		GetInvoiceDto response = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);
		
		return new SuccessDataResult<>(response, Messages.INVOICEFOUND);
	}
	
	
	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		
		checkIfPaymentIdExists(createInvoiceRequest.getPaymentId());
		
		Invoice invoice = setForAddMethod(createInvoiceRequest.getPaymentId());
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(Messages.INVOICEADD);
	}

	
	@Override
	public DataResult<List<GetInvoiceDto>> getByCustomerId(long customerId) {

		checkIfCustomerIdExists(customerId);

		List<Invoice> result = this.invoiceDao.findByCustomer_UserId(customerId);
		List<GetInvoiceDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, GetInvoiceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<>(response, Messages.INVOICEBYCUSTOMERLIST);
	}
	

	@Override
	public DataResult<List<GetInvoiceDto>> getByBetweenDates(LocalDate startDate, LocalDate endDate) {
		
		List<Invoice> result =  this.invoiceDao.findAllByRentDateLessThanEqualAndRentDateGreaterThanEqual(startDate, endDate);
		List<GetInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<>(response, Messages.INVOICELIST);
	}


	private Integer invoiceNumberCreator(Invoice invoice, long carRentalId) {
		
		GetCarRentalDto carRentalDto = this.carRentalService.getById(carRentalId).getData();
		
		String invoiceNumber = invoice.getCreateDate().getYear() +
				String.valueOf(invoice.getCreateDate().getMonthValue()) +
				invoice.getCreateDate().getDayOfMonth() +
				carRentalDto.getCustomerId() +
				carRentalId;
		
		return Integer.valueOf(invoiceNumber);
	}
	
	
	private Invoice setForAddMethod(long paymentId) {
		
		GetPaymentDto payment = this.paymentService.getById(paymentId).getData();
		GetCarRentalDto carRental = this.carRentalService.getById(payment.getCarRentalId()).getData();
		
		Invoice invoice = new Invoice();
		
		invoice.setInvoiceNumber(invoiceNumberCreator(invoice, payment.getCarRentalId()));  
		invoice.setCreateDate(LocalDate.now());
		invoice.setRentDate(carRental.getRentDate());
		invoice.setReturnDate(carRental.getReturnDate());
		invoice.setTotalDays((ChronoUnit.DAYS.between(carRental.getRentDate(), carRental.getReturnDate()) + 1)); 
		invoice.setRentTotalPrice(payment.getTotalPayment());

		
		Customer customer = new Customer();
        customer.setUserId(carRental.getCustomerId());
        invoice.setCustomer(customer);
        
        Payment pay = new Payment();
        pay.setId(paymentId);
        invoice.setPayment(pay);
        
		return invoice;
	}


	private void checkIfInvoiceIdExists(long invoiceId) {
		
		if (!this.invoiceDao.existsById(invoiceId)) {
			
			throw new BusinessException(Messages.INVOICENOTFOUND);
		}
	}
	
	private void checkIfPaymentIdExists(long paymentId) {  
		
		if (this.paymentService.getById(paymentId).getData() == null) {
			
			throw new BusinessException(Messages.PAYMENTNOTFOUND);
		}
	}

	private void checkIfCustomerIdExists(long customerId) {

		if (this.customerService.getById(customerId) == null) {

			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}
	}

	
}
