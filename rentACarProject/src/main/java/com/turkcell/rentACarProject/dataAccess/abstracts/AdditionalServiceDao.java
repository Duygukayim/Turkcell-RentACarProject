package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.turkcell.rentACarProject.entities.concretes.AdditionalService;

@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {

	AdditionalService getAdditionalServiceById(int id);

	AdditionalService getAdditionalServiceByName(String name);
	
}
