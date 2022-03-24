package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

	Invoice getInvoiceById (int id);
	
	List<Invoice> getByCustomer_CustomerId(int customerId);

	List<Invoice> findAllByRentDateLessThanEqualAndRentDateGreaterThanEqual(LocalDate rentDate, LocalDate returnDate);
	
	boolean existsByCustomer_CustomerId(int customerId);

	boolean existsByPayment_Id(int paymentId);

	boolean existsByCarRental_Id(int carRentalId);

	
}
