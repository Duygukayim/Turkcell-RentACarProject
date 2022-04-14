package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

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
import com.turkcell.rentACarProject.business.dtos.get.GetColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")

public class ColorsController {

	private final ColorService colorService;

	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping("/getAll")
	public DataResult<List<GetColorDto>> getAll() {
		
		return colorService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetColorDto> get(@RequestParam long id) {
		
		return colorService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateColorRequest createRequest) {
		
		return this.colorService.add(createRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id) {
		
		return this.colorService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam  long id, @RequestBody @Valid UpdateColorRequest updateRequest) {
		
		return this.colorService.update(id, updateRequest);
	}

}
