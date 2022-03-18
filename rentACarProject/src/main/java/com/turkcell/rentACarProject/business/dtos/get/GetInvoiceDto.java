package com.turkcell.rentACarProject.business.dtos.get;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.turkcell.rentACarProject.business.dtos.list.ListBrandDto;
import com.turkcell.rentACarProject.entities.concretes.CarRental;
import com.turkcell.rentACarProject.entities.concretes.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInvoiceDto {
	
	private int id;
	
    private int invoiceNumber;

	private LocalDate createDate;

    private LocalDate rentDate;

    private LocalDate returnDate;

	private int totalDays;

	private double rentTotalPrice;
    
    private int customerId;
    
    private int carRentalId;

}
