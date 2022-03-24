package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Long> {

	CorporateCustomer findByUserId(long userId);

    CorporateCustomer findByEmail(String email);
    
}
