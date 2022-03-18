package com.turkcell.rentACarProject.business.requests.car;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

    @NotNull
    @Positive
	private int id;
    
	@NotNull
	@Min(50)
	@Max(1000)
	private double dailyPrice;
	
	@NotNull
	@Min(2015)
	@Max(2022)
	private double modelYear;
	
	@Size(min = 2, max = 100)
	private String description;
	
    @NotNull
    @Positive
	private int brandId;
    
    @NotNull
    @Positive
	private int colorId;

    
}
