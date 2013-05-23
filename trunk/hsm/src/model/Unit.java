package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * This class represents the structure elements. Depending on the tier it
 * represents a building, an apartment or a room.
 * 
 * @author D20018
 * 
 */

public class Unit {

	// ---------------------------------------
	// Structure variables

	// root element of the structure is the same for all other elements

	// representing the child element
	private static List<String> nameList = new ArrayList<String>();
	private final List<Booking> bookingList = new ArrayList<Booking>();
	// ---------------------------------------

	private int unitID;
	private String name;
	private float sqrFeet;
	private final int MAX_OCCUPANCY = 3;
	private int numberOfUnits;
	private boolean hasChild;
	private boolean isOccupied = false;
	private float pricePerNight;
	private float pricePerMonth;
	private Unit superiorUnit;

	// Standard Constructor for the root element and buildings
	public Unit(String name) {
		if (checkName(name)) {
			this.sqrFeet = 0;
			this.name = name;
			this.pricePerNight = 0;
			this.pricePerMonth = 0;
			this.hasChild = false;
		} else {
			JOptionPane.showMessageDialog(null,
					"Please choose an other name. This one is already used!",
					"Invalid Unit Name", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Constructor for apartments and rooms
	public Unit(String name, int sqrFeet, float pricePerNight,
			float pricePerMonth) {
		if (checkName(name)) {
			this.sqrFeet = sqrFeet;
			this.name = name;
			this.pricePerNight = pricePerNight;
			this.pricePerMonth = pricePerMonth;
			this.hasChild = false;
		} else {
			JOptionPane.showMessageDialog(null,
					"Please choose an other name. This one is already used!",
					"Invalid Unit Name", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Überprüfung, ob der Lagername bereits in Verwendung ist
	 * 
	 * @param str
	 *            Zu überprüfender Name.
	 * @return true, wenn der Name verwendet werden kann.
	 */
	private boolean checkName(String str) {
		if (nameList.isEmpty())
			return true;
		else {
			for (String name : nameList) {
				if (str.equals(name))
					return false;
			}
		}
		return true;
	}

	// Getter & Setter

	public void addBooking(Booking b) {
		bookingList.add(b);
	}


	/**
	 * @return the nameList
	 */
	public static List<String> getNameList() {
		return nameList;
	}

	/**
	 * @param nameList the nameList to set
	 */
	public static void setNameList(List<String> nameList) {
		Unit.nameList = nameList;
	}

	/**
	 * @return the unitID
	 */
	public int getUnitID() {
		return unitID;
	}

	/**
	 * @param unitID the unitID to set
	 */
	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sqrFeet
	 */
	public float getSqrFeet() {
		return sqrFeet;
	}

	/**
	 * @param sqrFeet the sqrFeet to set
	 */
	public void setSqrFeet(float sqrFeet) {
		this.sqrFeet = sqrFeet;
	}

	/**
	 * @return the numberOfUnits
	 */
	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	/**
	 * @param numberOfUnits the numberOfUnits to set
	 */
	public void setNumberOfUnits(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	/**
	 * @return the hasChild
	 */
	public boolean isHasChild() {
		return hasChild;
	}

	/**
	 * @param hasChild the hasChild to set
	 */
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	/**
	 * @return the isOccupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}

	/**
	 * @param isOccupied the isOccupied to set
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	/**
	 * @return the pricePerNight
	 */
	public float getPricePerNight() {
		return pricePerNight;
	}

	/**
	 * @param pricePerNight the pricePerNight to set
	 */
	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	/**
	 * @return the pricePerMonth
	 */
	public float getPricePerMonth() {
		return pricePerMonth;
	}

	/**
	 * @param pricePerMonth the pricePerMonth to set
	 */
	public void setPricePerMonth(float pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}

	/**
	 * @return the bookingList
	 */
	public List<Booking> getBookingList() {
		return bookingList;
	}

	/**
	 * @return the mAX_OCCUPANCY
	 */
	public int getMAX_OCCUPANCY() {
		return MAX_OCCUPANCY;
	}

	/**
	 * @return the superiorUnit
	 */
	public Unit getSuperiorUnit() {
		return superiorUnit;
	}

	/**
	 * @param superiorUnit
	 *            the superiorUnit to set
	 */
	public void setSuperiorUnit(Unit superiorUnit) {
		this.superiorUnit = superiorUnit;
	}

	/**
	 * Get all finished bookings in a list.
	 * @return
	 */
	public List<Booking> getFinishedBookings() {
		List<Booking> list = new ArrayList<>();
		for (int i = 0; i < bookingList.size(); i++) {
			if (!(bookingList.get(i).getTotalCosts() == 0)) {
				list.add(bookingList.get(i));
			}
		}
		return list;
	}
}
