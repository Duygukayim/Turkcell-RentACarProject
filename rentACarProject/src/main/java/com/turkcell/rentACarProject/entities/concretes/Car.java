package com.turkcell.rentACarProject.entities.concretes;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.turkcell.rentACarProject.business.constants.CarStatus;

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
@Table(name = "CARS")
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "status", nullable = false)
    private CarStatus status;

	@Column(name = "daily_price", nullable = false)
	private double dailyPrice;

	@Column(name = "model_year", nullable = false)
	private int modelYear;

	@Column(name = "description", length = 64)
	private String description;

	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "color_id", nullable = false)
	private Color color;
	
	@Column(name = "mileage")
	private double mileage;

	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CarMaintenance> carMaintenances;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CarRental> carRentals; 
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CarDamage> carDamages;

}
