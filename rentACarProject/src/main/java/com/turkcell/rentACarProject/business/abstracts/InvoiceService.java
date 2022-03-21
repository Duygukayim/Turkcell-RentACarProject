package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListInvoiceDto;
import com.turkcell.rentACarProject.business.requests.invoice.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.invoice.DeleteInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface InvoiceService {

	DataResult<List<ListInvoiceDto>> getAll();

	DataResult<GetInvoiceDto> getById(int id);
	
	DataResult<List<ListInvoiceDto>> getByCustomerId(int customerId);
	
	Result add(CreateInvoiceRequest createInvoiceRequest);

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);


	
}
