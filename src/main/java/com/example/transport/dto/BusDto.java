package com.example.transport.dto;

import java.util.List;

public class BusDto {

	private int id;
	private String busCode;
	private List<PassengerDto> passengers;
	private int passengerCount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBusCode() {
		return busCode;
	}
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	public List<PassengerDto> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerDto> passengers) {
		this.passengers = passengers;
	}
	public int getPassengerCount() {
		return passengerCount;
	}
	public void setPassengerCount(int passengerCount) {
		this.passengerCount = passengerCount;
	}
}
