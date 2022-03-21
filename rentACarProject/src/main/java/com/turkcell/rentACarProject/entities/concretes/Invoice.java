package com.turkcell.rentACarProject.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="invoices")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private int id;
	
	@Column(name = "invoice_number", nullable = false, unique = true)
    private int invoiceNumber;
	
	@Column(name = "create_date")
	private LocalDate createDate = LocalDate.now();
	
	@Column(name = "rent_date")
    private LocalDate rentDate;

    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(name= "total_days")
	private int totalDays;
    
    @Column(name = "rent_total_price")
	private double rentTotalPrice;
    
    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;
    
    @OneToOne
    @JoinColumn(name = "car_rental_id", nullable = false)
    private CarRental carRental;
    
    @OneToOne
    @JoinColumn(name = "payment_id")
	private Payment payment;
	
}
