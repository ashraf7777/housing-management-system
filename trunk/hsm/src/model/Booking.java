package model;

import java.util.Date;

/*
 * This class provides all necessary information about a booking of one or more rooms.
 */

public class Booking {
	
	private static int bookingCounter = 1;
	private int bookingID;
	private int numberOfPersons;
	private String nameOfBooker;
	//TODO: Eventuell paymentType aufgliedern in Subtypes
	private String paymentType;
	private Date checkInDate;
	private Date checkOutDate;
	private Unit[] room;
	
	//Constructor
	Booking(String nameOfBooker, int numberOfPersons, Unit[] room, Date checkInDate, String paymentType)
	{
		this.bookingID = bookingCounter++;
		this.nameOfBooker = nameOfBooker;
		this.numberOfPersons = numberOfPersons;
		this.room = room;
		this.checkInDate = checkInDate;
		this.paymentType = paymentType;
	}
	
	
	
	//Getter & Setter
	
	public int getNumberOfPersons() {
		return numberOfPersons;
	}
	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}
	public String getNameOfBooker() {
		return nameOfBooker;
	}
	public void setNameOfBooker(String nameOfBooker) {
		this.nameOfBooker = nameOfBooker;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public Unit[] getRoom() {
		return room;
	}
	public void setRoom(Unit[] room) {
		this.room = room;
	}
	public int getBookingID() {
		return bookingID;
	}
	
	

}
