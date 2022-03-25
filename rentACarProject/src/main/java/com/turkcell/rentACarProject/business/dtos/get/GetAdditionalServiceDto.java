package com.turkcell.rentACarProject.business.dtos.get;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetAdditionalServiceDto {

	private String name;
	
	private double dailyPrice;
}
