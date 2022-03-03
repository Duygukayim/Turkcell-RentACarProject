package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {

	Car getCarById(int id);

	List<Car> findByDailyPriceLessThanEqual(double dailyPrice);
	
	boolean existsByDailyPrice(double dailyPrice);

    boolean existsByModelYear(int modelYear);

    boolean existsByDescription(String description);

    boolean existsByBrand_Id(int brandId);

    boolean existsByColor_Id(int colorId);

}
