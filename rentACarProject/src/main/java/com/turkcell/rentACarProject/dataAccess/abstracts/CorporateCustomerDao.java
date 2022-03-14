package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;

public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {

}
