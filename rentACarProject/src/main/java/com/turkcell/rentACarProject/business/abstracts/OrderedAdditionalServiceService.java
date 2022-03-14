package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import javax.validation.Valid;

import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;


public interface OrderedAdditionalServiceService {

	Result add(@Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);
	
	void add(List<ListOrderedAdditionalServiceDto> orderedAdditionalServiceIds, int id);

	Double calculateTotalPriceOfAdditionalServices(List<OrderedAdditionalService> orderedAdditionalServices);

	List<OrderedAdditionalService> getByCarRentalId(int carRentalId);



}
