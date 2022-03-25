package com.turkcell.rentACarProject.api.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.dtos.get.GetInvoiceDto;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	private InvoiceService invoiceService;

	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<GetInvoiceDto>> getAll() {

		return invoiceService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetInvoiceDto> get(@RequestParam long id) {

		return invoiceService.getById(id);
	}

	@GetMapping("/getByCustomerId")
	public DataResult<List<GetInvoiceDto>> getByCustomerId(@RequestParam long customerId) {

		return this.invoiceService.getByCustomerId(customerId);
	}

	@GetMapping("/getByBetweenDates")
	public DataResult<List<GetInvoiceDto>> getByBetweenDates(@RequestParam LocalDate endDate, @RequestParam LocalDate startDate) {

		return invoiceService.getByBetweenDates(endDate, startDate);
	}


}
