package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;


/*
 * This class represents the structure elements.
 * Depending on the tier it represents a building, an apartment or a room.
 */

public class Unit extends DefaultMutableTreeNode{

	//---------------------------------------
	//Structure variables
	
	//root element of the structure is the same for all other elements
	private static Unit root;
	
	//representing the child element
	private Unit leaf;
	private static List<String> nameList = new ArrayList<String>();
	//---------------------------------------
	
	private static final long serialVersionUID = 1L;
	private int unitID;
	private String name;
	private float sqrFeet;
	private int numberOfUnits;
	private boolean hasChild;
	private boolean isOccupied;
	private float pricePerNight;
	private float pricePerMonth;
	
	Unit(String name, int sqrFeet)
	{
		super(name);
		if (checkName(name))
		{
			this.sqrFeet = sqrFeet;
			this.name = name;
			this.hasChild = false;
		}
		else
		{
			//TODO: Fehlerausgabe (Exception)
		}
	}

	
	/**
	 * Fügt dem Baum ein neues Element hinzu und gibt dieses Lager dann zurück.
	 * 
	 * @param bez
	 *            Name des Elements (Lagername)
	 * @return Das soeben erstellte Lager
	 */
	public Unit addTreeElement(String name, int sqrFeet) {
		leaf = new Unit(name, sqrFeet);
		this.add(leaf);
		this.isOccupied = false; // übergeordneter Knoten darf keinen Bestand zeigen - falls diese Methode an einem Blatt aufgerufen wurde, ist dieses ebenfalls nicht mehr fähig einen Bestand zu halten und anzuzeigen

		return leaf;
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
	
	
	
	
	//Getter & Setter
	
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
}
