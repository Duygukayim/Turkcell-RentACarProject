package com.turkcell.rentACarProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CustomerService;
import com.turkcell.rentACarProject.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACarProject.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService {

	private final CustomerDao customerDao;
	
	@Autowired
	public CustomerManager(CustomerDao customerDao) {
		
		this.customerDao = customerDao;
	}

	@Override
	public Customer getById(long id) {
		
		return customerDao.findByUserId(id);
	}

}
