package com.turkcell.rentACarProject.business.dtos.get;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    
    private long paymentId;

}
