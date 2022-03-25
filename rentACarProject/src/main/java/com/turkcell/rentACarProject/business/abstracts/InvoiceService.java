package com.turkcell.rentACarProject.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetInvoiceDto;
import com.turkcell.rentACarProject.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface InvoiceService {

	DataResult<List<GetInvoiceDto>> getAll();

	DataResult<GetInvoiceDto> getById(long id);
	
	DataResult<List<GetInvoiceDto>> getByCustomerId(long customerId);
	
	DataResult<List<GetInvoiceDto>> getByBetweenDates(LocalDate startDate, LocalDate endDate);
	
	Result add(CreateInvoiceRequest createRequest);

}
