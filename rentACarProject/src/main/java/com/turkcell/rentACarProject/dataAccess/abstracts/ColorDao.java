package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Color;

@Repository
public interface ColorDao extends JpaRepository<Color, Long> {
	
	Color findById(long id);

    Color findByName(String name);

}
