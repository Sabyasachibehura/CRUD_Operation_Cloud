package com.example.demo.repo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Ticket;
import com.example.demo.repo.TicketBookingDAO;

@Service
public class TicketServiceImpl implements TicketBookingService{
	
	@Autowired
	private TicketBookingDAO ticketBookingDAO;

	@Override
	public Ticket saveTicket(Ticket ticket) {
		return ticketBookingDAO.save(ticket);
	}

	@Override
	public Ticket getTicket(int id) {
		Optional<Ticket> ticket = ticketBookingDAO.findById(id);
		return ticket.get();
		 
	}

	@Override
	public void deleteTicket(int id) {
		ticketBookingDAO.deleteById(id);
	}

	@Override
	public List<Ticket> getAllTicket() {
		Iterable<Ticket> all = ticketBookingDAO.findAll();
		return (List<Ticket>) all;
	}

}
