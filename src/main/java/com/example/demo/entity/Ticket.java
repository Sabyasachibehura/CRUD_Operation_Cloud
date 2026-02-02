package com.example.demo.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;  

@Entity
@Table(name="ticket")
public class Ticket implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	//IDENTITY ==> Databaes only generates primary key JPA will not do any activity
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ticket_id")
	private Integer ticketId;
	@Column(name="pass_name")
	private String passengerName;
	//@Column(name="booking_date",nullable = false)
	@Column(name="booking_date")
    private String bookingDate;
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
    
    
}
