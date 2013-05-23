package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
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


	/**
	 * @return the bookingID
	 */
	public int getBookingID() {
		return bookingID;
	}

	/**
	 * @param bookingID the bookingID to set
	 */
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	/**
	 * @return the totalCosts
	 */
	public float getTotalCosts() {
		return totalCosts;
	}

	/**
	 * @param totalCosts the totalCosts to set
	 */
	public void setTotalCosts(float totalCosts) {
		this.totalCosts = totalCosts;
	}

	/**
	 * @return the firstNameOfBooker
	 */
	public String getFirstNameOfBooker() {
		return firstNameOfBooker;
	}

	/**
	 * @param firstNameOfBooker the firstNameOfBooker to set
	 */
	public void setFirstNameOfBooker(String firstNameOfBooker) {
		this.firstNameOfBooker = firstNameOfBooker;
	}

	/**
	 * @return the lastNameOfBooker
	 */
	public String getLastNameOfBooker() {
		return lastNameOfBooker;
	}

	/**
	 * @param lastNameOfBooker the lastNameOfBooker to set
	 */
	public void setLastNameOfBooker(String lastNameOfBooker) {
		this.lastNameOfBooker = lastNameOfBooker;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the paymentType
	 */
	public Payment getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(Payment paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the checkInDate
	 */
	public Date getCheckInDate() {
		return checkInDate;
	}

	/**
	 * @param checkInDate the checkInDate to set
	 */
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	/**
	 * @return the checkOutDate
	 */
	public Date getCheckOutDate() {
		return checkOutDate;
	}

	/**
	 * @param checkOutDate the checkOutDate to set
	 */
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	/**
	 * @return the room
	 */
	public Unit getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Unit room) {
		this.room = room;
	}

	/**
	 * @param numberOfPersons the numberOfPersons to set
	 */
	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	/**
	 * Returns objects for the tables
	 * @return objects formated for the tables
	 */
	public Object[] returnObjectForCheckIn() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		Object[] data = {
				this.room.getSuperiorUnit().getSuperiorUnit().getName(),
				this.room.getSuperiorUnit().getName(), this.room.getName(),
				this.lastNameOfBooker, this.firstNameOfBooker,
				sdf.format(this.checkInDate), this.paymentType.getName(), sdf.format(this.birthday) };
		return data;
	}
	public Object[] returnObjectForHome(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		Object[] data = {this.room.getName(),this.lastNameOfBooker, this.firstNameOfBooker,
				sdf.format(this.checkInDate), sdf.format(this.checkOutDate), this.totalCosts };
		return data;
	}
	
}
