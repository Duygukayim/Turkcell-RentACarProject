package com.turkcell.rentACarProject.business.dtos.list;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListInvoiceDto {
	
	private int id;
	
    private int invoiceNumber;

	private LocalDate createDate;

    private LocalDate rentDate;

    private LocalDate returnDate;

	private long totalDays;

	private double rentTotalPrice;
    
    private int customerId;
    
    private int carRentalId;

}
