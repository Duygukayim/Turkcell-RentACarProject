package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Long> {
	
	IndividualCustomer findByUserId(long userId);

    IndividualCustomer findByEmail(String email);

}
