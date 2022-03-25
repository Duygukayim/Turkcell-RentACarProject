package com.turkcell.rentACarProject.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.get.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListInvoiceDto;
import com.turkcell.rentACarProject.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.invoice.DeleteInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACarProject.entities.concretes.Invoice;
import com.turkcell.rentACarProject.entities.concretes.Payment;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private CarRentalService carRentalService;
	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;

	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, CarRentalService carRentalService, PaymentDao paymentDao, ModelMapperService modelMapperService) {
		this.invoiceDao = invoiceDao;
		this.carRentalService = carRentalService;
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
	}


	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		List<Invoice> result = invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.INVOICELIST);
	}


	@Override
	public DataResult<GetInvoiceDto> getById(int id) {

		Invoice invoice = this.invoiceDao.getById(id);
		checkIfInvoiceIdExists(invoice.getId());
		
		GetInvoiceDto response = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);
		
		return new SuccessDataResult<GetInvoiceDto>(response, Messages.INVOICEFOUND);	
	}
	
	
	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		
		checkIfPaymentIdExists(createInvoiceRequest.getPaymentId());
		checkIfCustomerIdExists(createInvoiceRequest.getCustomerId());
		
		Invoice invoice = setForAddMethod(createInvoiceRequest.getPaymentId());
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(Messages.INVOICEADD);
	}


	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		
		checkIfInvoiceIdExists(deleteInvoiceRequest.getId());
		
		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		this.invoiceDao.delete(invoice);
		
		return new SuccessResult(Messages.INVOICEDELETE);
	}


	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		
		checkIfInvoiceIdExists(updateInvoiceRequest.getId());
		checkIfPaymentIdExists(updateInvoiceRequest.getPaymentId());
		checkIfCustomerIdExists(updateInvoiceRequest.getCustomerId());
		
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(Messages.INVOICEUPDATE);
	}
	
	@Override
	public DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId) {

		checkIfCustomerIdExists(customerId);
		
		List<Invoice> result = this.invoiceDao.getByCustomer_CustomerId(customerId);
		List<ListInvoiceDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.INVOICEBYCUSTOMERLIST);
	}
	

	@Override
	public DataResult<List<ListInvoiceDto>> getByBetweenDates(LocalDate startDate, LocalDate endDate) {
		
		List<Invoice> result =  invoiceDao.findAllByRentDateLessThanEqualAndRentDateGreaterThanEqual(startDate, endDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice,ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, Messages.INVOICELIST);
	}


	private Integer invoiceNumberCreator(Invoice invoice, int carRentalId) throws BusinessException {
		
		GetCarRentalDto carRentalDto = this.carRentalService.getById(carRentalId).getData();
		
		String invoiceNumber = String.valueOf(carRentalDto.getRentCityId() + invoice.getCreateDate().getYear()) +
				String.valueOf(invoice.getCreateDate().getMonthValue()) +
				String.valueOf(invoice.getCreateDate().getDayOfMonth()) + 
				String.valueOf(carRentalDto.getCustomerId())+
				String.valueOf(carRentalId);
		
		return Integer.valueOf(invoiceNumber);
	}
	
	
	private Invoice setForAddMethod(int paymentId) {
		
		Payment payment = this.paymentDao.getById(paymentId);
		GetCarRentalDto carRental = this.carRentalService.getById(payment.getCarRental().getId()).getData();
		
		Invoice invoice = new Invoice();
		
		invoice.setInvoiceNumber(invoiceNumberCreator(invoice, carRental.getId()));
		invoice.setCreateDate(LocalDate.now());
		invoice.setRentDate(carRental.getRentDate());
		invoice.setReturnDate(carRental.getReturnDate());
		invoice.setTotalDays((ChronoUnit.DAYS.between(carRental.getRentDate(), carRental.getReturnDate()) + 1)); 
		invoice.setRentTotalPrice(carRentalService.calTotalPrice(carRental.getId())); 
		
		return invoice;
	}


	private void checkIfInvoiceIdExists(int invoiceId) {
		
		if (!this.invoiceDao.existsById(invoiceId)) {
			throw new BusinessException(Messages.INVOICENOTFOUND);
		}
	}
	
	private void checkIfPaymentIdExists(int paymentId) {  
		
		if (!this.invoiceDao.existsByPayment_Id(paymentId)) {
			throw new BusinessException(Messages.CARRENTALNOTFOUND);
		}
	}
	
	private void checkIfCustomerIdExists(int customerId) {
		
		if (!this.invoiceDao.existsByCustomer_CustomerId(customerId)) {
			throw new BusinessException(Messages.CUSTOMERNOTFOUND);
		}
	}
	
	
}
