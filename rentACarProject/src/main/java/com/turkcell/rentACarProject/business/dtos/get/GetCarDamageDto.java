package com.turkcell.rentACarProject.business.dtos.get;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCarDamageDto {

	private String description;

	private long carId;

}
