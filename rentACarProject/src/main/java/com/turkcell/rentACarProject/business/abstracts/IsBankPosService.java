package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface IsBankPosService {
	
	Result isBankPosService(String cardOwnerName, String cardNumber, int cardCvvNumber);

}
