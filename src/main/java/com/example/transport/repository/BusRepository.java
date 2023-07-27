package com.example.transport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transport.domain.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {

	List<Bus> findByBusCodeIgnoreCaseContaining(String searchText);
}
