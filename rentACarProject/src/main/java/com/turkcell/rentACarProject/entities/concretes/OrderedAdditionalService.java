package com.turkcell.rentACarProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordered_additional_services")
@Entity
public class OrderedAdditionalService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "additional_service_id")
	private AdditionalService additionalService;
	
    @Column(name = "quantity")
    private int quantity;
    
    @ManyToOne()
    @JoinColumn(name="car_rental_id")
    private CarRental carRental;
	
}