package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Long> {

	Set<OrderedAdditionalService> findByCarRental_Id(long carRentalId);

	List<OrderedAdditionalService> getByCarRentalId(long carRentalId);
	

}
