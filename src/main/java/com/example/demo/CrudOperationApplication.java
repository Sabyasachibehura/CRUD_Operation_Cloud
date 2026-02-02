package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketBookingService;

@SpringBootApplication
public class CrudOperationApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CrudOperationApplication.class, args);
	}
	@Autowired
	private TicketBookingService ticketBookingService;
	
	@Override
	public void run(String... args) throws Exception {
//		Ticket t=new Ticket();
//		t.setPassengerName("Sabyaschi");
//		t.setBookingDate("20/11/2024");
//
//		ticketBookingService.saveTicket(t);
		
		
	}

}
