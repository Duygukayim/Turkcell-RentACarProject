package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.entities.concretes.Customer;

public interface CustomerService {

	Customer getById(long id);
}
