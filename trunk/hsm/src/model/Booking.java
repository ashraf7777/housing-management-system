package model;

import java.util.Date;

/*
 * This class provides all necessary information about a booking of one or more rooms.
 */

public class Booking {
	
	private static int bookingCounter = 1;
	private int bookingID;
	private int numberOfPersons;
	private String firstNameOfBooker;
	private String lastNameOfBooker;
	private String street;
	private String city;
	private String zipCode;
	private Date birthday;
	//TODO: Eventuell paymentType aufgliedern in Subtypes
	private String paymentType;
	private Date checkInDate;
	private Date checkOutDate;
	private Unit[] room;
	
	/**
	 * 
	 * @param firstNameOfBooker Firstname
	 * @param lastNameOfBooker	Lastname
	 * @param birthday	Birthday
	 * @param street	Street
	 * @param city	City
	 * @param zipCode Zip Code
	 * @param numberOfPersons Number of Persons
	 * @param room Room 
	 * @param checkInDate Check-In Date
	 * @param paymentType PaymentType
	 */
	public Booking(String firstNameOfBooker, String lastNameOfBooker, Date birthday, String street, String city, String zipCode, int numberOfPersons, Unit[] room, Date checkInDate, String paymentType)
	{
		this.bookingID = bookingCounter++;
		this.firstNameOfBooker = firstNameOfBooker;
		this.lastNameOfBooker = lastNameOfBooker;
		this.birthday = birthday;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.numberOfPersons = numberOfPersons;
		this.room = room;
		this.checkInDate = checkInDate;
		this.paymentType = paymentType;
	}
	
	
	
	//Getter & Setter



	public 

int getNumberOfPersons() {
		return numberOfPersons;
	}
	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}
	public String getFirstNameOfBooker() {
		return firstNameOfBooker;
	}
	public String getLastNameofBooker() {
		return lastNameOfBooker;
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
	
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public Date getBirthday() {
		return birthday;
	}
}
