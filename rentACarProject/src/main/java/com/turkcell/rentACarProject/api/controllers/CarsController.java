package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.get.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.list.ListCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/getAll")
	public DataResult<List<ListCarDto>> getAll() {
		return carService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCarDto> get(@RequestParam int id) {
		return carService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest) throws BusinessException {
		return this.carService.add(createCarRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteCarRequest deleteCarRequest) throws BusinessException {
		return this.carService.delete(deleteCarRequest);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateCarRequest updateCarRequest) throws BusinessException {
		return this.carService.update(updateCarRequest);
	}

	@GetMapping("/getCarByDailyPrice")
	DataResult<List<ListCarDto>> getAllByDailyPriceLessThanEqual(@RequestParam double dailyPrice) {
		return this.carService.getAllByDailyPriceLessThanEqual(dailyPrice);
	}

	@GetMapping("/getAllPaged")
	DataResult<List<ListCarDto>> getAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) {
		return this.carService.getAllPaged(pageNumber, pageSize);
	}

	@GetMapping("/getAllSorted")
	DataResult<List<ListCarDto>> getAllSorted(@RequestParam("direction") Sort.Direction direction) {
		return this.carService.getAllSorted(direction);
	}

}
