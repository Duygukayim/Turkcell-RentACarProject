package com.turkcell.rentACarProject.business.dtos.list;

import java.util.List;

import com.turkcell.rentACarProject.entities.concretes.OrderedAdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCustomerDto {
	
	private int id;
	
    private String firstName;

    private String lastName;

//    private List<OrderedAdditionalService> orderedAdditionalServices;
    
}
