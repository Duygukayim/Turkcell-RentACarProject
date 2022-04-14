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

import com.turkcell.rentACarProject.business.abstracts.CardInfoService;
import com.turkcell.rentACarProject.business.dtos.get.GetCardInfoDto;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.business.requests.cardInfo.UpdateCardInfoRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cardInfos")
public class CardInfosController {
	
	private final CardInfoService cardInfoService;

	@Autowired
	public CardInfosController(CardInfoService cardInfoService) {
		this.cardInfoService = cardInfoService;
	}

	@GetMapping("/get/all")
	public DataResult<List<GetCardInfoDto>> getAll() {
		
		return cardInfoService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<GetCardInfoDto> get(@RequestParam long id) {
		
		return cardInfoService.getById(id);
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCardInfoRequest createRequest, @RequestParam long customerId) {
		
		return cardInfoService.add(createRequest, customerId);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestParam long id ) {
		
		return cardInfoService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestParam long id, @RequestBody @Valid UpdateCardInfoRequest updateRequest) {
		
		return cardInfoService.update(id, updateRequest);
	}

}
