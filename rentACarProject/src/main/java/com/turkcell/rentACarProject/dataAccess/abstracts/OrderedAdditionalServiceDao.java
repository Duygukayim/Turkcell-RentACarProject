package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer> {

	List<OrderedAdditionalService> findByCarRental_Id(int carRentalId);

	List<OrderedAdditionalService> getByCarRentalId(int carRentalId);
	

}
