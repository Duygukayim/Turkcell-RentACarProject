package com.turkcell.rentACarProject.business.requests.car;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

	@NotNull
	@Positive
	private double dailyPrice;

	@NotNull
	@Min(2000)
	@Max(2022)
	private int modelYear;

//	@NotNull
	private double mileage;

	@Size(min = 2, max = 64)
	private String description;

	@NotNull
	@Positive
	private long brandId;

	@NotNull
	@Positive
	private long colorId;

}
