package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketBookingService;

@RestController
public class TicketController {

	@Autowired
	private TicketBookingService ticketService;
	 
	@PostMapping("/saveTicket")
	public Ticket saveTicket(@RequestBody Ticket ticket) {
		return ticketService.saveTicket(ticket);
	}
	@GetMapping("/getlocation/{id}")
	public Ticket getTicketById(@PathVariable("id") int id) {
		return ticketService.getTicket(id);
	}
	@DeleteMapping("/deletelocation/{id}")
	public void deleteTicketById(@PathVariable("id") int id) {
		 ticketService.deleteTicket(id);
	}
	@PutMapping("/updatelocation")
	public Ticket updateTicketById(@RequestBody Ticket ticket) {
		return ticketService.saveTicket(ticket);
	}
	@GetMapping("/")
	public List<Ticket> getAllTicket() {
		return ticketService.getAllTicket();
	}
}
