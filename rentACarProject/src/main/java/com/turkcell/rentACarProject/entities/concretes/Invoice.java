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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INVOICES")
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	
	@Column(name = "invoice_number", nullable = false, unique = true)
    private int invoiceNumber;
	
	@Column(name = "create_date")
	private LocalDate createDate = LocalDate.now();
	
	@Column(name = "rent_date")
    private LocalDate rentDate;

    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(name= "total_days")
	private Long totalDays;
    
    @Column(name = "rent_total_price")
	private double rentTotalPrice;
    
    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;
    
    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
}
