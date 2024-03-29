package com.turkcell.rentACarProject.business.concretes;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACarProject.entities.concretes.CarRental;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private final AdditionalServiceService additionalServiceService;
	private final ModelMapperService modelMapperService;

	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao, AdditionalServiceService additionalServiceService, ModelMapperService modelMapperService) {
		
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.additionalServiceService = additionalServiceService;
		this.modelMapperService = modelMapperService;
	}

	
	@Override
	public void add(Set<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequest, long carRentalId) {
		
		for (CreateOrderedAdditionalServiceRequest createOrderedAdditionalRequest : createOrderedAdditionalServiceRequest) {
			
			checkIfAdditionalServiceIdExists(createOrderedAdditionalRequest.getAdditionalServiceId());

            OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest, OrderedAdditionalService.class);

            CarRental carRental = new CarRental();
            carRental.setId(carRentalId);
            orderedAdditionalService.setCarRental(carRental);
           
            this.orderedAdditionalServiceDao.save(orderedAdditionalService);
 
		}
	}

	
	@Override
	public Set<OrderedAdditionalService> getByCarRentalId(long carRentalId) {
        
		return this.orderedAdditionalServiceDao.findByCarRental_Id(carRentalId);
    }
	
	
	@Override
    public Double calDailyTotal(Set<OrderedAdditionalService> orderedAdditionalServices) {
		
        double dailyTotal = 0;

        for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {

            dailyTotal += orderedAdditionalService.getQuantity() * orderedAdditionalService.getAdditionalService().getDailyPrice();
        }

        return dailyTotal;
    }
	
	private void checkIfAdditionalServiceIdExists(long additionalServiceId) {
		
		if (this.additionalServiceService.getById(additionalServiceId) == null) {
				
			throw new BusinessException(Messages.ADDITIONALSERVICENOTFOUND);
		}
	}
	
}
