package com.turkcell.rentACarProject.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalServices")
public class OrderedAdditionalServicesController {
	
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}
	

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}

}
