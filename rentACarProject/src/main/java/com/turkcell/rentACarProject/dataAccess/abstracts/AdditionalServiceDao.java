package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.turkcell.rentACarProject.entities.concretes.AdditionalService;

@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Long> {

	AdditionalService findById(long id);

	AdditionalService findByName(String name);
	
}
