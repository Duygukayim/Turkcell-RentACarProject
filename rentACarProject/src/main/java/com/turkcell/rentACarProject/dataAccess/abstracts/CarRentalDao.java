package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.CarRental;

@Repository
public interface CarRentalDao extends JpaRepository<CarRental, Long> {

	CarRental findById(long id);

	List<CarRental> findByCar_Id(long carId);
	
	List<CarRental> findByCustomer_UserId(long customerId);

}
