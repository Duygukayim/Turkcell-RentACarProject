package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.ListCarDto;
import com.turkcell.rentACarProject.business.requests.car.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.car.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.car.UpdateCarRequest;

@RestController
@RequestMapping("/api/cars")
public class CarController {

	private CarService carService;

	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/getall")
	public List<ListCarDto> getAll() {
		return this.carService.getAll();
	}

	@PostMapping("/create")
	public void add(@RequestBody CreateCarRequest createCarRequest) {
		this.carService.create(createCarRequest);
	}

	@DeleteMapping("/delete")
	public void delete(@RequestBody DeleteCarRequest deleteCarRequest) {
		this.carService.delete(deleteCarRequest);
	}

	@PutMapping("/update")
	public void update(@RequestBody UpdateCarRequest updateCarRequest) {
		this.carService.update(updateCarRequest);
	}

	@GetMapping("/get")
	public GetCarDto get(@RequestParam int id) {
		return this.carService.getById(id);
	}
}
