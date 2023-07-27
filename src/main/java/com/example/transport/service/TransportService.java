package com.example.transport.service;

import java.util.List;

import com.example.transport.dto.BusDto;
import com.example.transport.dto.PassengerDto;

public interface TransportService {

	public BusDto addOrUpdateBus(BusDto busDto);
	
	public PassengerDto savePassengersForABus(PassengerDto passenger);
	
	public PassengerDto updatePassenger(PassengerDto passengerDto);
	
	public Boolean deleteBusDetails(int busId);
	
	public Boolean deletePassenger(int passengerId);
	
	public List<PassengerDto> fetchAllPassengers();
	
	public List<BusDto> fetchAllTransportDetails();
	
	public List<PassengerDto> fetchPassengersForABus(int busId);
	
	public List<BusDto> filterTransportDetails(String searchText);
	
	public List<PassengerDto> filterPassengerDetails(String searchText);
	
	public List<PassengerDto> filterPassengerDetailsForABus(int busId, String searchText);
}
