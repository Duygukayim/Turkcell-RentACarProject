package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.CarDamage;

@Repository
public interface CarDamageDao extends JpaRepository<CarDamage, Long> {
	
	CarDamage findById(long id);
	
	List<CarDamage> findByCar_Id(long carId);

}
