package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.FindColorDto;
import com.turkcell.rentACarProject.business.dtos.ListColorDto;
import com.turkcell.rentACarProject.business.requests.CreateColorRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;

@RestController
@RequestMapping("/api/colors")

public class ColorsController {
	
	private ColorService colorService;
	
	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}
	
	@GetMapping("/listall")
	public List<ListColorDto> listAll(){
		return this.colorService.listAll();
	}
	
	@PostMapping("/create")
	public void add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException{
		this.colorService.create(createColorRequest);
	}
	
	@GetMapping("/find")
	public FindColorDto find(@RequestParam int colorId) throws BusinessException {
		return this.colorService.findById(colorId);
	}
}
