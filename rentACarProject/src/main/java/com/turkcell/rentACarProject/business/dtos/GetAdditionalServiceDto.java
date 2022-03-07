package com.turkcell.rentACarProject.business.dtos;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAdditionalServiceDto {

	private int id;
	private String name;
}
