package com.turkcell.rentACarProject.business.dtos.get;

import com.turkcell.rentACarProject.entities.concretes.CardInfo;

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
public class GetPaymentDto {
	
	private long CarRentalId;
	
	private double totalPayment;

	private long cardInfoId;

}
