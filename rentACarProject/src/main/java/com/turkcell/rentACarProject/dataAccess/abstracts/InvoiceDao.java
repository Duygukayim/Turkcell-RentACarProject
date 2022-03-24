package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Long> {

	Invoice findById(long id);
	
	List<Invoice> findByCustomer_UserId(long customerId);

	List<Invoice> findAllByRentDateLessThanEqualAndRentDateGreaterThanEqual(LocalDate rentDate, LocalDate returnDate);
	
	boolean existsByCustomer_CustomerId(long customerId);

	boolean existsByPayment_Id(long paymentId);

	boolean existsByCarRental_Id(long carRentalId);

	
}
