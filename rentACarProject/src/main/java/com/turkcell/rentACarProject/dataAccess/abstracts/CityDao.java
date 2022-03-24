package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.City;

@Repository
public interface CityDao extends JpaRepository<City, Long> {
	
	City findById(long id);

    City findByName(String name);
	
}
