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
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")

public class ColorsController {

	private ColorService colorService;

	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping("/getAll")
	public DataResult<List<ListColorDto>> getAll() {
		return this.colorService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetColorDto> get(@RequestParam int id) throws BusinessException {
		return this.colorService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) {
		try {
			return this.colorService.add(createColorRequest);
		} catch (Exception e) {
			return new ErrorResult(e.getMessage());
		}
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) {
		try {
			return this.colorService.delete(deleteColorRequest);
		} catch (Exception e) {
			return new ErrorResult(e.getMessage());
		}
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) {
		try {
			return this.colorService.update(updateColorRequest);
		} catch (Exception e) {
			return new ErrorResult(e.getMessage());
		}
	}

}
