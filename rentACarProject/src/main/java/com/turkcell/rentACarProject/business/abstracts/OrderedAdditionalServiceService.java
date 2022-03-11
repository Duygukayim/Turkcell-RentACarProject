package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.dtos.get.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface OrderedAdditionalServiceService {

	DataResult<List<ListOrderedAdditionalServiceDto>> getAll();

	DataResult<GetOrderedAdditionalServiceDto> getById(int id);
	
	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException;

	Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException;

	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;
}
