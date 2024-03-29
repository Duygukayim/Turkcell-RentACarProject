package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {

	Customer findByUserId(long userId);
}
