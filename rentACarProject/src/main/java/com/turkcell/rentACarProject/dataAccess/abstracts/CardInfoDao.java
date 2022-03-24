package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.CardInfo;

@Repository
public interface CardInfoDao extends JpaRepository<CardInfo, Long> {
	
	CardInfo findById(long id);

	CardInfo getByCardHolderName(String cardHolderName);

}
