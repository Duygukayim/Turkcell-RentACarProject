package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.dtos.get.GetPaymentDto;
import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping("/get/all")
	public DataResult<List<GetPaymentDto>> getAll() {
		
		return paymentService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<GetPaymentDto> get(@RequestParam long id) {
		
		return paymentService.getById(id);
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreatePaymentRequest createRequest, @RequestParam("rememberMe") boolean rememberMe) {
		
		return this.paymentService.add(createRequest, rememberMe);
	}

}
