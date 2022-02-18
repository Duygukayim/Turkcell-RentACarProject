package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {

	Car getCarById(int id);
}
