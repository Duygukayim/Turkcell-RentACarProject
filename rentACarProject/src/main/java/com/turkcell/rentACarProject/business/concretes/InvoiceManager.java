package com.turkcell.rentACarProject.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarRentalService;
import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarRentalDto;
import com.turkcell.rentACarProject.business.dtos.get.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;
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
import com.turkcell.rentACarProject.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACarProject.entities.concretes.AdditionalService;
import com.turkcell.rentACarProject.entities.concretes.CarRental;
import com.turkcell.rentACarProject.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private CarRentalService carRentalService;
	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private IndividualCustomerService individualCustomerService;
	private ModelMapperService modelMapperService;

	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, CarRentalService carRentalService, OrderedAdditionalServiceDao orderedAdditionalServiceDao, IndividualCustomerService individualCustomerService, ModelMapperService modelMapperService) {
		this.invoiceDao = invoiceDao;
		this.carRentalService = carRentalService;
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.individualCustomerService = individualCustomerService;
		this.modelMapperService = modelMapperService;
	}


	@Override
	public DataResult<List<ListInvoiceDto>> getAll() {
		
		List<Invoice> result = invoiceDao.findAll();
		List<ListInvoiceDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response);
	}


	@Override
	public DataResult<GetInvoiceDto> getById(int id) {

		Invoice invoice = this.invoiceDao.getById(id);
		checkIfInvoiceIdExists(invoice.getId());
		GetInvoiceDto response = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);
		
		return new SuccessDataResult<GetInvoiceDto>(response, "Success");	
	}
	
	
	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		
		checkIfCarRentalIdExists(createInvoiceRequest.getCarRentalId());
		checkIfCustomerIdExists(createInvoiceRequest.getCustomerId());
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		setForAddMethod(invoice, createInvoiceRequest.getCarRentalId(), createInvoiceRequest.getCustomerId());
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Added : " + invoice.getInvoiceNumber());
	}


	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		
		Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
		checkIfInvoiceIdExists(invoice.getId());
		this.invoiceDao.delete(invoice);
		
		return new SuccessResult("Invoice.Deleted : " + invoice.getInvoiceNumber());
	}


	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		checkIfInvoiceIdExists(invoice.getId());
		checkIfCarRentalIdExists(invoice.getCarRental().getId());
		checkIfCustomerIdExists(invoice.getCustomer().getUserId());
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice.Updated : " + invoice.getInvoiceNumber());
	}
	
	@Override
	public DataResult<List<ListInvoiceDto>> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
		
		List<Invoice> result = this.invoiceDao.getAllByBetweenStartDateAndEndDate(startDate, endDate);
		List<ListInvoiceDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}
	
	@Override
	public DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId) {

		checkIfCustomerIdExists(customerId);
		List<Invoice> result = this.invoiceDao.getByCustomer_CustomerId(customerId);
		List<ListInvoiceDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, ListInvoiceDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ListInvoiceDto>>(response, "Success");
	}


	private void checkIfInvoiceIdExists(int invoiceId) {
		
		if (!this.invoiceDao.existsById(invoiceId)) {
			throw new BusinessException("An invoice with this ID was not found!");
		}
	}
	
	private void checkIfCarRentalIdExists(int carRentalId) {
		
		if (!this.invoiceDao.existsByCarRental_CarRentalId(carRentalId)) {
			throw new BusinessException("Rental Car with this ID was not found!");
		}
	}
	
	private void checkIfCustomerIdExists(int customerId) {
		
		if (!this.invoiceDao.existsByCustomer_CustomerId(customerId)) {
			throw new BusinessException("A customer with this ID was not found!");
		}
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

	
	private int calculateTotalDaysForRental(GetCarRentalDto carRental) {
		
		int days = 1;
		
		if(ChronoUnit.DAYS.between(carRental.getRentDate(), carRental.getReturnDate()) == 0) {
			return days;
		}
		
		days = Integer.valueOf((int) ChronoUnit.DAYS.between(carRental.getRentDate(), carRental.getReturnDate()));
		
		return days;
	}
	
	
	private double calculateDifferentCityPrice(CarRental carRental) {
		
		if (carRental.getRentCity().getId() != carRental.getReturnCity().getId()) {
			return 750;
		}
		
		return 0;
	}
	
	
	private double calculateTotalDailyPriceForRentalCar(CarRental carRental, int days) {
		
		double totalPrice = carRental.getDailyPrice() * days;
		
		return totalPrice;
	}
	
	
	private double calculateTotalDailyPriceForAdditionalService(ListOrderedAdditionalServiceDto orderedAdditionalService, AdditionalService additionalService,  int days) {
		
		double totalPrice = orderedAdditionalService.getQuantity() * additionalService.getDailyPrice() * days;
		
		return totalPrice;
	}
	
	
	private double calculateInvoiceTotalPrice(Invoice invoice) {
		
		return calculateTotalDailyPriceForRentalCar(invoice.getCarRental(), invoice.getTotalDays()) + calculateTotalDailyPriceForAdditionalService(null, null, invoice.getTotalDays()) + calculateDifferentCityPrice(invoice.getCarRental());
	}
	
	
	private Invoice setForAddMethod(Invoice invoice, int carRentalId, int userId) {
		
		var carRental = this.carRentalService.getById(carRentalId).getData();
		
		invoice.setInvoiceNumber(invoiceNumberCreator(invoice, carRentalId));
		invoice.setCreateDate(LocalDate.now());
		invoice.setRentDate(carRental.getRentDate());
		invoice.setReturnDate(carRental.getReturnDate());
		invoice.setTotalDays(calculateTotalDaysForRental(carRental));
		invoice.setRentTotalPrice(calculateInvoiceTotalPrice(invoice));
		
		return invoice;
	}
	
}
