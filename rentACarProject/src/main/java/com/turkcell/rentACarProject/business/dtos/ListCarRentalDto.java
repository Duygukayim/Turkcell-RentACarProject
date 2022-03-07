package com.turkcell.rentACarProject.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarRentalDto {

	private int id;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private int customerId;
    private int carId;
    
}
