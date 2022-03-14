package com.turkcell.rentACarProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer {
	
	@Column(name = "company_name")
    private String companyName;

    @Column(name = "tax_number")
    private String taxNumber;
	
}
