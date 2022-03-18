package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;


public interface OrderedAdditionalServiceService {

	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);

	DataResult<List<ListOrderedAdditionalServiceDto>> getByCarRentalId(int carRentalId);

	void checkIfCarRentalIdExists(int carRentalId);

}
