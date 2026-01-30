package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Ticket;

@Repository
public interface TicketBookingDAO extends CrudRepository<Ticket, Integer>{

	
}
