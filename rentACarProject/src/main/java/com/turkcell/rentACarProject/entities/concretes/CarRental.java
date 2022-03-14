package com.turkcell.rentACarProject.entities.concretes;

import java.time.LocalDate;
import java.util.List;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_rentals")
public class CarRental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "rent_date")
    private LocalDate rentDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name="customer_id")
    private int customerId;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car carId;
    
    @Column(name="daily_price")
    private double dailyPrice;
    
    @ManyToOne
    @JoinColumn(name = "rent_city_id")
    private City rentCity;
    
    @ManyToOne
    @JoinColumn(name = "return_city_id")
    private City returnCity;

    @OneToMany(mappedBy = "carRental")
    private List<OrderedAdditionalService> orderedAdditionalServices;
    
    
}
