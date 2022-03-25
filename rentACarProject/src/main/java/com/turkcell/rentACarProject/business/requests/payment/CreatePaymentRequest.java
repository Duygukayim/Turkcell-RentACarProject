package com.turkcell.rentACarProject.business.requests.payment;

import javax.validation.constraints.NotNull;

import com.turkcell.rentACarProject.business.requests.cardInfo.CreateCardInfoRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

	@NotNull
	private int CarRentalId;

	@NotNull
    private CreateCardInfoRequest cardInfo;

}
