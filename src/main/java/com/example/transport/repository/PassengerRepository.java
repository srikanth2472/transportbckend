package com.example.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transport.domain.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

	List<Passenger> findByIdIn(List<Integer> passengerIds);
	
	List<Passenger> findByBusId(int busId);
	
	List<Passenger> findByNameIgnoreCaseContainingOrEmailIgnoreCaseContaining(String searchText, String searchText1);
}
