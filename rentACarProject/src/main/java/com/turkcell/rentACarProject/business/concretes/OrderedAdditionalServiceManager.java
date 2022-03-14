package com.turkcell.rentACarProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACarProject.business.dtos.list.ListOrderedAdditionalServiceDto;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACarProject.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private CarRentalDao carRentalDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao, CarRentalDao carRentalDao, ModelMapperService modelMapperService) {
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.carRentalDao = carRentalDao;
		this.modelMapperService = modelMapperService;
	}

	
	
	@Override
	public void add(List<ListOrderedAdditionalServiceDto> orderedAdditionalServiceIds, int carRentalId) {
		
		for (ListOrderedAdditionalServiceDto requests : orderedAdditionalServiceIds) {
            OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(requests, OrderedAdditionalService.class);
            orderedAdditionalService.setCarRental(carRentalDao.getCarRentalById(carRentalId));
            this.orderedAdditionalServiceDao.save(orderedAdditionalService);
        }
	}
	
	
	@Override
    public List<OrderedAdditionalService> getByCarRentalId(int carRentalId) {
        return this.orderedAdditionalServiceDao.findByCarRental_Id(carRentalId);
    }

	@Override
	public Double calculateTotalPriceOfAdditionalServices(List<OrderedAdditionalService> orderedAdditionalServices) {
		
		double price = 0 ;
		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {
			price += orderedAdditionalService.getAdditionalService().getDailyPrice()*orderedAdditionalService.getQuantity();
		}
		return price;
	}

	
}
