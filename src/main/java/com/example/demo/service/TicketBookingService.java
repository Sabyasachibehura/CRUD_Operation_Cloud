package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Ticket;

public interface TicketBookingService {

	Ticket saveTicket(Ticket ticket);
	Ticket getTicket(int id);
	void deleteTicket(int id);
	List<Ticket> getAllTicket();
}
