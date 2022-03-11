package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.core.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

}
