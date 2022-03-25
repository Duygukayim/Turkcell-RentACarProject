package com.turkcell.rentACarProject.business.dtos.get;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetInvoiceDto {
	
    private int invoiceNumber;

	private LocalDate createDate;

    private LocalDate rentDate;

    private LocalDate returnDate;

	private long totalDays;

	private double rentTotalPrice;
    
    private long customerId;
    
    private long carRentalId;

}
