package com.turkcell.rentACarProject.business.abstracts;

import java.util.Set;

import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;


public interface OrderedAdditionalServiceService {

	Result add(Set<CreateOrderedAdditionalServiceRequest> createRequest);

	Set<OrderedAdditionalService> getByCarRentalId(long carRentalId);
	
	Double calDailyTotal(Set<OrderedAdditionalService> orderedAdditionalServices);


}
