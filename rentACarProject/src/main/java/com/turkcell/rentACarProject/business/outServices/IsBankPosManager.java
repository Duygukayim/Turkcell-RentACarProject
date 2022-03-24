package com.turkcell.rentACarProject.business.outServices;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;

@Service
public class IsBankPosManager {

	public Result makePayment(String cardHolderName, String cardNumber, String cardCvvNumber) {

		return new SuccessResult(Messages.POSISBANK);
	}

}
