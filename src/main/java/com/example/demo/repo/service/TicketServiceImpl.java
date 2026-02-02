package com.example.demo.repo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Ticket;
import com.example.demo.repo.TicketBookingDAO;

@Service
public class TicketServiceImpl implements TicketBookingService {

	@Autowired
	private TicketBookingDAO ticketBookingDAO;

	@Override
	@Transactional
	@CachePut(value = "users", key = "#result.id")
	public Ticket saveTicket(Ticket ticket) {
		System.out.println("✍️ WRITE → Updating DB & Cache");
		return ticketBookingDAO.save(ticket);
	}

	@Override
	@Cacheable(value = "users", key = "#id")
	@Transactional(readOnly = true)
	public Ticket getTicket(int id) {
		System.out.println("❌ Cache MISS → Going to RDS");
		System.out.println("📖 READ operation");
		Optional<Ticket> ticket = ticketBookingDAO.findById(id);
		return ticket.get();

	}

	@Override
	@Transactional
	@CacheEvict(value = "users", key = "#id")
	public void deleteTicket(int id) {
		System.out.println("🗑 Cache EVICT → DB delete");
		ticketBookingDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Ticket> getAllTicket() {
		System.out.println("📖 READ operation");
		Iterable<Ticket> all = ticketBookingDAO.findAll();
		return (List<Ticket>) all;
	}

}
