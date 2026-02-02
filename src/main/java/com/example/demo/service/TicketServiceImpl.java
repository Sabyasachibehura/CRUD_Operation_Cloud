package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.DataSourceContextHolder;
import com.example.demo.config.DataSourceContextHolder.DataSourceType;
import com.example.demo.entity.Ticket;
import com.example.demo.repo.TicketBookingDAO;

@Service
public class TicketServiceImpl implements TicketBookingService {

	@Autowired
	private TicketBookingDAO ticketBookingDAO;

//	@Override
//	@Transactional
//	@CachePut(value = "users", key = "#result.id")
//	public Ticket saveTicket(Ticket ticket) {
//		System.out.println("✍️ WRITE → Updating DB & Cache");
//		try {
//	        DataSourceContextHolder.set(DataSourceType.MASTER); // force master
//	        return ticketBookingDAO.save(ticket);
//	    } finally {
//	        DataSourceContextHolder.clear();
//	    }
//		
//		//return ticketBookingDAO.save(ticket);
//	}
	@Override
	@Transactional
	@CachePut(value = "users", key = "#result.ticketId")
	public Ticket saveTicket(Ticket ticket) {
	    System.out.println("✍️ WRITE → Updating DB & Cache");

	    try {
	        // Force MASTER for write operations
	        DataSourceContextHolder.set(DataSourceType.MASTER);

	        Ticket masterTicket;

	        if (ticket.getTicketId() != null) {
	            // Fetch existing entity from MASTER to prevent stale object issues
	            Optional<Ticket> existing = ticketBookingDAO.findById(ticket.getTicketId());
	            if (existing.isPresent()) {
	                masterTicket = existing.get();
	                // Update only the fields you have
	                masterTicket.setPassengerName(ticket.getPassengerName());
	                masterTicket.setBookingDate(ticket.getBookingDate());
	            } else {
	                // ID doesn't exist, treat as new
	                masterTicket = ticket;
	            }
	        } else {
	            // New entity
	            masterTicket = ticket;
	        }

	        return ticketBookingDAO.save(masterTicket);

	    } finally {
	        DataSourceContextHolder.clear();
	    }
	}



//	@Override
//	@Cacheable(value = "users", key = "#id")
//	@Transactional(readOnly = true)
//	public Ticket getTicket(int id) {
////		System.out.println("❌ Cache MISS → Going to RDS");
////		System.out.println("📖 READ operation");
////		Optional<Ticket> ticket = ticketBookingDAO.findById(id);
////		return ticket.get();
//		System.out.println("📖 READ operation (replica ok)");
//	    return ticketBookingDAO.findById(id)
//	        .orElseThrow(() -> new RuntimeException("Ticket not found"));
//
//	}
	
	@Override
    @Cacheable(value = "users", key = "#id")
    @Transactional(readOnly = true)
    public Ticket getTicket(int id) {
        System.out.println("❌ Cache MISS → Going to RDS (REPLICA)");
        System.out.println("📖 READ operation");

        try {
            DataSourceContextHolder.set(DataSourceType.READER);
            Optional<Ticket> ticket = ticketBookingDAO.findById(id);
            return ticket.orElseThrow(() -> new RuntimeException("Ticket not found"));
        } finally {
            DataSourceContextHolder.clear();
        }
    }

//	@Override
//	@Transactional
//	@CacheEvict(value = "users", key = "#id")
//	public void deleteTicket(int id) {
//		System.out.println("🗑 Cache EVICT → DB delete");
//		ticketBookingDAO.deleteById(id);
//	}
	
	@Override
    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public void deleteTicket(int id) {
        System.out.println("🗑 Cache EVICT → DB delete");

        try {
            DataSourceContextHolder.set(DataSourceType.MASTER);

            // Fetch from MASTER to avoid stale object issues
            Ticket masterTicket = ticketBookingDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ticket not found in MASTER DB"));
            ticketBookingDAO.delete(masterTicket);
        } finally {
            DataSourceContextHolder.clear();
        }
    }


//	@Override
//	@Transactional(readOnly = true)
//	public List<Ticket> getAllTicket() {
//		System.out.println("📖 READ operation");
//		Iterable<Ticket> all = ticketBookingDAO.findAll();
//		return (List<Ticket>) all;
//	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Ticket> getAllTicket() {
        System.out.println("📖 READ operation");

        try {
            DataSourceContextHolder.set(DataSourceType.READER);
            Iterable<Ticket> all = ticketBookingDAO.findAll();
            return (List<Ticket>) all;
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}




