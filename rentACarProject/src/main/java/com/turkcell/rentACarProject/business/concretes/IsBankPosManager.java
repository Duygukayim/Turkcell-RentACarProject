package com.turkcell.rentACarProject.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.IsBankPosService;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;

@Service
public class IsBankPosManager implements IsBankPosService {

	@Override
	public Result isBankPosService(String cardOwnerName, String cardNumber, int cardCvvNumber) {
		
		return new SuccessResult("The payment was made through Is Bank.");
	}

}
