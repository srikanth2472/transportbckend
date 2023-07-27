package com.example.transport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.transport.dto.BusDto;
import com.example.transport.dto.PassengerDto;
import com.example.transport.service.TransportService;

@RestController
@RequestMapping("/transport")
@CrossOrigin("*")
public class TransportController {

	@Autowired
	private TransportService transportService;
	
	@PostMapping("add-or-update-bus")
	public ResponseEntity<BusDto> addOrUpdateBus(@RequestBody BusDto busDto) {
		return new ResponseEntity<BusDto>(transportService.addOrUpdateBus(busDto), HttpStatus.OK);
	}
	
	@PostMapping("save-passenger-for-a-bus")
	public ResponseEntity<PassengerDto> savePassengersForABus(@RequestBody PassengerDto passenger) {
		return new ResponseEntity<PassengerDto>(transportService.savePassengersForABus(passenger), HttpStatus.OK);
	}
	
	@PutMapping("update-passenger")
	public ResponseEntity<PassengerDto> updatePassenger(@RequestBody PassengerDto passengerDto) {
		return new ResponseEntity<PassengerDto>(transportService.updatePassenger(passengerDto), HttpStatus.OK);
	}
	
	@DeleteMapping("delete-bus-details")
	public ResponseEntity<Boolean> deleteBusDetails(@RequestParam("busId") Integer busId) {
		return new ResponseEntity<Boolean>(transportService.deleteBusDetails(busId), HttpStatus.OK);
	}
	
	@DeleteMapping("delete-passenger")
	public ResponseEntity<Boolean> deletePassengers(@RequestParam Integer passengerId) {
		return new ResponseEntity<Boolean>(transportService.deletePassenger(passengerId), HttpStatus.OK);
	}
	
	@GetMapping("fetch-all-passenger-details")
	public ResponseEntity<List<PassengerDto>> fetchAllPassengerDetails() {
		return new ResponseEntity<List<PassengerDto>>(transportService.fetchAllPassengers(), HttpStatus.OK);
	}
	
	@GetMapping("fetch-all-transport-details")
	public ResponseEntity<List<BusDto>> fetchAllTransportDetails() {
		return new ResponseEntity<List<BusDto>>(transportService.fetchAllTransportDetails(), HttpStatus.OK);
	}
	
	@GetMapping("fetch-passenger-details-for-a-bus")
	public ResponseEntity<List<PassengerDto>> fetchPassengersForABus(@RequestParam Integer busId) {
		return new ResponseEntity<List<PassengerDto>>(transportService.fetchPassengersForABus(busId), HttpStatus.OK);
	}
	
	@GetMapping("search-by-bus-code")
	public ResponseEntity<List<BusDto>> fetchTransportDetailsWithSearchByBusCode(@RequestParam String searchText) {
		return new ResponseEntity<List<BusDto>>(transportService.filterTransportDetails(searchText), HttpStatus.OK);
	}
	
	@GetMapping("search-by-passenger-details")
	public ResponseEntity<List<PassengerDto>> filterPassengerDetails(@RequestParam String searchText) {
		return new ResponseEntity<List<PassengerDto>>(transportService.filterPassengerDetails(searchText), HttpStatus.OK);
	}
	
	@GetMapping("search-by-passenger-details-for-a-bus")
	public ResponseEntity<List<PassengerDto>> filterPassengerDetails(@RequestParam Integer busId, @RequestParam String searchText) {
		return new ResponseEntity<List<PassengerDto>>(transportService.filterPassengerDetailsForABus(busId, searchText), HttpStatus.OK);
	}
	
}
