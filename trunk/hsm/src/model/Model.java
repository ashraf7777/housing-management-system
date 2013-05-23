package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which stores our data for the runtime.
 * All data are lost after closing the application.
 * @author T16879
 *
 */
public class Model {

	//Lists to store the data
	private List<Booking> allBookings = new ArrayList<Booking>();  
	private List<Receipt> allReceipts = new ArrayList<Receipt>();
	//Map to improve the search for bookings if you have the unit
	private Map<Unit, Booking> assignmentUnitBooking = new HashMap<Unit, Booking>();
	private TreeDataModel root;
	
	
	/**
	 * Get all stored bookings from this runtime
	 * @return
	 * 		List of all bookings
	 */
	public List<Booking> getAllBookings() {
		return allBookings;
	}

	/**
	 * Add new booking to the list
	 * @param b 
	 * 		new booking which i to be added to the list
	 */
	public void addBookingToList(Booking b) {
		allBookings.add(b);
	}
	
	
	public Booking getBookingFromRoom(Unit room) {
		return assignmentUnitBooking.get(room);
	}
	
	public void addBookingToRoom(Booking b, Unit r)
	{
		assignmentUnitBooking.put(r, b);
	}

	public TreeDataModel getRoot() {
		return root;
	}

	public void setRoot(TreeDataModel root) {
		this.root = root;
	}

	public List<Receipt> getAllReceipts() {
		return allReceipts;
	}

	public void addReceipts(Receipt receipts) {
		allReceipts.add(receipts);
	}
	
	
	
	
	
}
