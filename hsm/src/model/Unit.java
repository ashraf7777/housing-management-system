package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/*
 * This class represents the structure elements.
 * Depending on the tier it represents a building, an apartment or a room.
 */

public class Unit {

	// ---------------------------------------
	// Structure variables

	// root element of the structure is the same for all other elements
	private static Unit root;

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
			// TODO: Fehlerausgabe (Exception)
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
			// TODO: Fehlerausgabe (Exception)
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

	public static Unit getTree() {
		return root;
	}

	public void addBooking(Booking b) {
		bookingList.add(b);
	}

	public List<Booking> getBooking() {
		return bookingList;
	}

	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public float getSqrFeet() {
		return sqrFeet;
	}

	public void setSqrFeet(float sqrFeet) {
		this.sqrFeet = sqrFeet;
	}

	public float getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public float getPricePerMonth() {
		return pricePerMonth;
	}

	public void setPricePerMonth(float pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

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

	public List<Booking> getFinishedBookings() {
		List<Booking> list = new ArrayList<>();
		for (int i = 0; i < bookingList.size(); i++) {
			if (!(bookingList.get(i).getCheckOutDate() == null)) {
				list.add(bookingList.get(i));
			}
		}
		return list;
	}
}
