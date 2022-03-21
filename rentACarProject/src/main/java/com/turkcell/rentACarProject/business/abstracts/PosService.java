package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.requests.payment.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface PosService {

	Result payment(CreatePaymentRequest createPaymentRequest);
}
