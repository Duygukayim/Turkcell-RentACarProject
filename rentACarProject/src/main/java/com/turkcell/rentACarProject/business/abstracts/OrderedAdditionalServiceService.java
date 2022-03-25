package com.turkcell.rentACarProject.business.abstracts;

import java.util.Set;

import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;


public interface OrderedAdditionalServiceService {

	void add(Set<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequest, long carRentalId);
	
	Set<OrderedAdditionalService> getByCarRentalId(long carRentalId);
	
	Double calDailyTotal(Set<OrderedAdditionalService> orderedAdditionalServices);



}
