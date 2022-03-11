package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACarProject.entities.concretes.AdditionalService;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {
	
	AdditionalService getAdditionalServiceById(int id);
	
	AdditionalService getAdditionalServiceByName(String name);
	
}
