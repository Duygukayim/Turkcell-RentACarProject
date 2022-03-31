package com.turkcell.rentACarProject.business.outServices;

import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;

public class IsBankPosManager {

	public Result makePayment(CreateCardInfoRequest createRequest) {

		return new SuccessResult(Messages.POSISBANK);
	}

}
