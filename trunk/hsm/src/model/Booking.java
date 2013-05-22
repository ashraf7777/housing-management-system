package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This class provides all necessary information about a booking of one or more rooms.
 */

public class Booking {

	private static int bookingCounter = 1;
	private int bookingID;
	private int numberOfPersons;
	private float totalCosts;
	private String firstNameOfBooker;
	private String lastNameOfBooker;
	private String street;
	private String city;
	private String zipCode;
	private Date birthday;
	// TODO: Eventuell paymentType aufgliedern in Subtypes
	private Payment paymentType;
	private Date checkInDate;
	private Date checkOutDate;
	private Unit room;

	/**
	 * 
	 * @param firstNameOfBooker
	 *            Firstname
	 * @param lastNameOfBooker
	 *            Lastname
	 * @param birthday
	 *            Birthday
	 * @param street
	 *            Street
	 * @param city
	 *            City
	 * @param zipCode
	 *            Zip Code
	 * @param numberOfPersons
	 *            Number of Persons
	 * @param room
	 *            Room
	 * @param checkInDate
	 *            Check-In Date
	 * @param paymentTyp
	 *            PaymentType
	 */
	public Booking(String firstNameOfBooker, String lastNameOfBooker,
			Date birthday, String street, String city, String zipCode,
			int numberOfPersons, Unit room, Date checkInDate, Payment paymentTyp) {
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
		this.paymentType = paymentTyp;
	}

	// Getter & Setter

	public Booking() {
		this.bookingID = bookingCounter++;
	}

	public int getNumberOfPersons() {
		return numberOfPersons;
	}

	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	public String getFirstNameOfBooker() {
		return firstNameOfBooker;
	}

	public Payment getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Payment paymentType) {
		this.paymentType = paymentType;
	}

	public float getTotalCosts() {
		return totalCosts;
	}

	public void setTotalCosts(float totalCosts) {
		this.totalCosts = totalCosts;
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

	public Unit getRoom() {
		return room;
	}

	public void setRoom(Unit room) {
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

	public String getLastNameOfBooker() {
		return lastNameOfBooker;
	}

	public void setLastNameOfBooker(String lastNameOfBooker) {
		this.lastNameOfBooker = lastNameOfBooker;
	}

	public void setFirstNameOfBooker(String firstNameOfBooker) {
		this.firstNameOfBooker = firstNameOfBooker;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Object[] returnObject() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		Object[] data = {
				this.room.getSuperiorUnit().getSuperiorUnit().getName(),
				this.room.getSuperiorUnit().getName(), this.room.getName(),
				this.lastNameOfBooker, this.firstNameOfBooker,
				sdf.format(this.checkInDate), this.paymentType.getName(), sdf.format(this.birthday) };
		return data;
	}
}