package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACarProject.entities.concretes.Payment;

@Repository
@Transactional
public interface PaymentDao extends JpaRepository<Payment, Integer> {
	
	Payment getPaymentById(int id);
	
	Payment getAllByCarRental_Id(int carRentalId);
	
}
