package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {
	

}
