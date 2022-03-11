package com.turkcell.rentACarProject.business.requests.orderedAdditionalService;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderedAdditionalServiceRequest {

	@NotNull
	private int id;
	
}
