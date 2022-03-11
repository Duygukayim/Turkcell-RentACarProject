package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.dtos.get.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;

	public OrderedAdditionalServiceManager(AdditionalServiceDao additionalServiceDao, OrderedAdditionalServiceDao orderedAdditionalServiceDao, ModelMapperService modelMapperService) {
		this.additionalServiceDao = additionalServiceDao;
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		List<OrderedAdditionalService> result = orderedAdditionalServiceDao.findAll();
		List<ListOrderedAdditionalServiceDto> response = result.stream()
				.map(orderedAdditionalService -> modelMapperService.forDto().map(orderedAdditionalService,
						ListOrderedAdditionalServiceDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceDto> getById(int id) {
		OrderedAdditionalService orderedAdditionalService = orderedAdditionalServiceDao.getById(id);
		if (!checkIfOrderedAdditionalServiceId(orderedAdditionalService.getId())) {
			return new ErrorDataResult<GetOrderedAdditionalServiceDto>(
					"OrderedAdditionalService.NotFound , Ordered Additional Service with this ID was not found!");
		}
		GetOrderedAdditionalServiceDto response = modelMapperService.forDto().map(orderedAdditionalService,
				GetOrderedAdditionalServiceDto.class);
		return new SuccessDataResult<GetOrderedAdditionalServiceDto>(response, "Success");
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest,
				OrderedAdditionalService.class);
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		return new SuccessResult("OrderedAdditionalService.Added : " + orderedAdditionalService.getId());
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(deleteOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		if (checkIfOrderedAdditionalServiceId(orderedAdditionalService.getId())) {
			this.orderedAdditionalServiceDao.deleteById(orderedAdditionalService.getId());
			return new SuccessResult("OrderedAdditionalService.Deleted : " + orderedAdditionalService.getId());
		}
		return new ErrorResult("OrderedAdditionalService.NotDeleted : " + orderedAdditionalService.getId() + " , Additional Service with given ID not exists!");
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		if (!checkIfOrderedAdditionalServiceId(orderedAdditionalService.getId())) {
			return new ErrorResult("OrderedAdditionalService.NotUpdated : " + orderedAdditionalService.getId() + " , Ordered Additional Service with given ID not exists!");
		}
		if(!checkIfAdditionalServiceId(orderedAdditionalService.getAdditionalService().getId())) {
			return new ErrorResult("OrderedAdditionalService.NotUpdated : " + orderedAdditionalService.getId() + " , Additional Service with given ID not exists!");
		}
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		return new SuccessResult("OrderedAdditionalService.Updated : " + orderedAdditionalService.getId());
	}

	private boolean checkIfOrderedAdditionalServiceId(int orderedAdditionalServiceId) {
		return Objects.nonNull(orderedAdditionalServiceDao.getOrderedAdditionalServiceById(orderedAdditionalServiceId));
	}

	private boolean checkIfAdditionalServiceId(int additionalServiceId) throws BusinessException {
		return Objects.nonNull(additionalServiceDao.getAdditionalServiceById(additionalServiceId));
	}
	
}
