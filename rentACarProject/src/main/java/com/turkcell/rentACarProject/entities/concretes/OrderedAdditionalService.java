package com.turkcell.rentACarProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ORDERED_ADDITIONAL_SERVICES")
public class OrderedAdditionalService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "quantity")
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "additional_service_id", nullable = false)
	private AdditionalService additionalService;
    
    @ManyToOne
    @JoinColumn(name="car_rental_id", nullable = false)
    private CarRental carRental;
	
}
