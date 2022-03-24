package com.turkcell.rentACarProject.business.requests.cardInfo;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCardInfoRequest {
	
	@NotNull
	private int id;

}
