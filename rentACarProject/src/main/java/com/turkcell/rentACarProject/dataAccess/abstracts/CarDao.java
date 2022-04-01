package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Long> {

	Car findById(long id);

	List<Car> findByDailyPriceLessThanEqual(double dailyPrice);
	
	List<Car> findByBrand_Id(long id);
	
	List<Car> findByColor_Id(long id);
	
	Car findByAllCar(long brandId, long colorId, int modelYear, double dailyPrice, String description);

}
