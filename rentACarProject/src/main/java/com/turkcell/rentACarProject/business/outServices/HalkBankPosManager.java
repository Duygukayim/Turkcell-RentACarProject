package com.turkcell.rentACarProject.business.outServices;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;

@Service
public class HalkBankPosManager {

	public Result odemeYap(String cardNumber, String cardHolderName, String cardCvvNumber) {

		return new SuccessResult(Messages.POSHALKBANK);
	}

}
