package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.dtos.get.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalServices")
public class OrderedAdditionalServicesController {
	
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		return orderedAdditionalServiceService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetOrderedAdditionalServiceDto> getById(@RequestParam int id) {
		return orderedAdditionalServiceService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws BusinessException {
		return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	}
	

}
