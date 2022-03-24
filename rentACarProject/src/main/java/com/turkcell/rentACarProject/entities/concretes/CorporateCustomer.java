package com.turkcell.rentACarProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "CORPORATE_CUSTOMERS")
public class CorporateCustomer extends Customer {
	
    @Column(name = "company_name", length = 64, nullable = false)
    private String companyName;

    @Column(name = "tax_number", length = 10, nullable = false, unique = true)
    private String taxNumber;
	
}
