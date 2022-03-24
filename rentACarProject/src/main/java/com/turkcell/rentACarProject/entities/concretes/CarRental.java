package com.turkcell.rentACarProject.entities.concretes;

import java.time.LocalDate;
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
@Table(name = "CAR_RENTALS")
public class CarRental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;
    
    @Column(name = "starting_mileage")
    private int startingMileage;
    
    @Column(name = "return_mileage")
    private int returnMileage;
    
    @ManyToOne
    @JoinColumn(name = "return_city_id", nullable = false)
    private City returnCity;

    @ManyToOne
    @JoinColumn(name = "rent_city_id", nullable = false)
    private City rentCity;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    
    @OneToMany(mappedBy = "carRental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderedAdditionalService> orderedAdditionalServices;
    
    @OneToMany(mappedBy ="carRental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Payment> payments;
    
    
}
