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

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.dtos.ListColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;

@RestController
@RequestMapping("/api/colors")

public class ColorsController {
	
	private ColorService colorService;
	
	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public List<ListColorDto> getAll(){
		return this.colorService.getAll();
	}
	
	@PostMapping("/create")
	public void add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException{
		this.colorService.create(createColorRequest);
	}
	
	@DeleteMapping("/delete")
	public void delete(@RequestBody DeleteColorRequest deleteColorRequest) {
		this.colorService.delete(deleteColorRequest);
	}
	
	@PutMapping("/update")
	public void update(@RequestBody UpdateColorRequest updateColorRequest) {
		this.colorService.update(updateColorRequest);
	}
	
	@GetMapping("/get")
	public GetColorDto get(@RequestParam int id) throws BusinessException {
		return this.colorService.getById(id);
	}
}
