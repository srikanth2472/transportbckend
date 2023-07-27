package com.example.transport.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.transport.domain.Bus;
import com.example.transport.domain.Passenger;
import com.example.transport.dto.BusDto;
import com.example.transport.dto.PassengerDto;
import com.example.transport.exception.TransportException;
import com.example.transport.repository.BusRepository;
import com.example.transport.repository.PassengerRepository;

@Service
public class TransportServiceImpl implements TransportService {

	@Autowired
	private BusRepository busRepo;
	
	@Autowired
	private PassengerRepository passengerRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SessionFactory factory;
	
	@Override
	public BusDto addOrUpdateBus(BusDto busDto) {
		try {
			Bus bus = new Bus();
			bus.setId(busDto.getId());
			bus.setBusCode(busDto.getBusCode());
			busRepo.save(bus);
			return busDto;
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public PassengerDto savePassengersForABus(PassengerDto passenger) {
		try {
			Passenger passengerEntity = new Passenger();
			BeanUtils.copyProperties(passenger, passengerEntity);
			passengerEntity.setBus(busRepo.findById(passenger.getBusId()).get());
			passengerRepo.save(passengerEntity);
			return passenger;
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public PassengerDto updatePassenger(PassengerDto passengerDto) {
		try {
			String query="update passenger set name='"+passengerDto.getName()+"',email='"+passengerDto.getEmail()+
					"' where id='"+passengerDto.getId()+"' ";
			jdbcTemplate.update(query);
			return passengerDto;
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public Boolean deleteBusDetails(int busId) {
		try {
			busRepo.deleteById(busId);
			return true;
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public Boolean deletePassenger(int passengerId) {
		try {
			String query="delete from passenger where id='"+passengerId+"' ";
			jdbcTemplate.update(query);
			return true;
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public List<PassengerDto> fetchAllPassengers() {
		try {
			String sql = "select p.id, p.name, p.email, b.bus_code from passenger p inner join bus b on b.id = p.bus_id";
			List<PassengerDto> passengerDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<PassengerDto>(PassengerDto.class));
			if (passengerDtoList != null && !passengerDtoList.isEmpty()) {
				return passengerDtoList;
			} else {
				throw new TransportException("Passengers not found, please add a passenger");
			}
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public List<BusDto> fetchAllTransportDetails() {
		try {
			String sql = "select b.id, b.bus_code, count(p.bus_id) as passenger_count from bus b left outer join passenger p on b.id = p.bus_id group by b.id order by b.id asc";
			List<BusDto> busDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<BusDto>(BusDto.class));
//			Session session = factory.openSession();
//			Criteria cr = session.createCriteria(Bus.class);
//			ProjectionList columns = Projections.projectionList()
//					.add(Projections.groupProperty("id"))
//					.add(Projections.property("busCode"));
//			cr.setProjection(columns);
//			List<BusDto> busDtoList = cr.list();
			if (busDtoList != null && !busDtoList.isEmpty()) {
				return busDtoList;
			} else {
				throw new TransportException("Buses not found, please add a bus");
			}
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public List<PassengerDto> fetchPassengersForABus(int busId) {
		try {
			String sql = "select p.id, p.name, p.email, b.bus_code from passenger p inner join bus b on b.id = p.bus_id where p.bus_id = "+busId;
			List<PassengerDto> passengerDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<PassengerDto>(PassengerDto.class));
			if (passengerDtoList != null && !passengerDtoList.isEmpty()) {
				return passengerDtoList;
			} else {
				throw new TransportException("Passengers not found, please add a passenger");
			}
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public List<BusDto> filterTransportDetails(String searchText) {
		try {
			String sql = "SELECT b.id, b.bus_code, count(p.bus_id) as passenger_count FROM bus b left outer join passenger p on b.id = p.bus_id where lower(b.bus_code) like lower('%"
						+searchText+"%') group by p.bus_id order by b.id asc";
			List<BusDto> busDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<BusDto>(BusDto.class));
			if (busDtoList != null && !busDtoList.isEmpty()) {
				return busDtoList;
			} else {
				throw new TransportException("Buses not found with given text");
			}
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public List<PassengerDto> filterPassengerDetails(String searchText) {
		try {
			String sql = "SELECT p.id, p.name, p.email, p.bus_id, b.bus_code FROM passenger p inner join bus b on b.id = p.bus_id where lower(p.name) like lower('%"
					+ searchText + "%') or lower(p.email) like lower('%" + searchText + "%')";
			List<PassengerDto> passengerDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<PassengerDto>(PassengerDto.class));
			if (passengerDtoList != null && !passengerDtoList.isEmpty()) {
				return passengerDtoList;
			} else {
				throw new TransportException("Passengers not found with given text");
			}
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
	
	@Override
	public List<PassengerDto> filterPassengerDetailsForABus(int busId, String searchText) {
		try {
			String sql = "SELECT p.id, p.name, p.email, p.bus_id, b.bus_code FROM passenger p inner join bus b on b.id = p.bus_id where (lower(p.name) like lower('%"
					+ searchText + "%') or lower(p.email) like lower('%" + searchText + "%')) and p.bus_id = " + busId;
			List<PassengerDto> passengerDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<PassengerDto>(PassengerDto.class));
			if (passengerDtoList != null && !passengerDtoList.isEmpty()) {
				return passengerDtoList;
			} else {
				throw new TransportException("Passengers not found with given text");
			}
		} catch(Exception e) {
			throw new TransportException(e.getMessage());
		}
	}
}
