package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.turkcell.rentACarProject.entities.concretes.Car;
import com.turkcell.rentACarProject.entities.concretes.CarRental;

@Repository
public interface CarRentalDao extends JpaRepository<CarRental, Integer> {

	CarRental getCarRentalById(int id);

	List<CarRental> getCarRentalsByCarId(Car car);

	List<CarRental> getCarRentalByCustomerId(int id);

}
