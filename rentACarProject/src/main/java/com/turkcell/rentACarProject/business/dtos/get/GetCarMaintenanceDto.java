package com.turkcell.rentACarProject.business.dtos.get;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetCarMaintenanceDto {
	
	private String description;
	
	private LocalDate returnDate;
	
	private long carId;
	
	private String brandName;
	
	private String colorName;
	
}
