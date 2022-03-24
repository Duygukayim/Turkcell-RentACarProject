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
	
	boolean existsByDailyPrice(double dailyPrice);

    boolean existsByModelYear(int modelYear);

    boolean existsByDescription(String description);

    boolean existsByBrand_Id(long brandId);

    boolean existsByColor_Id(long colorId);

}
