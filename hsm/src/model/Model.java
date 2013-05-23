package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which stores our data for the runtime. All data are lost after closing
 * the application.
 * 
 * @author T16879
 * 
 */
public class Model {

	// Lists to store the data
	private List<Booking> allBookings = new ArrayList<Booking>();
	private List<Receipt> allReceipts = new ArrayList<Receipt>();
	// Map to improve the search for bookings if you have the unit
	private Map<Unit, Booking> assignmentUnitBooking = new HashMap<Unit, Booking>();
	private TreeDataModel root;

	/**
	 * Get all stored bookings from this runtime
	 * 
	 * @return List of all bookings
	 */
	public List<Booking> getAllBookings() {
		return allBookings;
	}

	/**
	 * Add new booking to the list
	 * 
	 * @param b
	 *            new booking which i to be added to the list
	 */
	public void addBookingToList(Booking b) {
		allBookings.add(b);
	}

	/**
	 * Get a booking wich is assigned to a room
	 * 
	 * @param room
	 * @return the booking which belongs to the room
	 */
	public Booking getBookingFromRoom(Unit room) {
		return assignmentUnitBooking.get(room);
	}

	/**
	 * Add a new assignment into the Map
	 * 
	 * @param b
	 *            the booking element is the value element
	 * @param r
	 *            the room element is the key element
	 */
	public void addBookingToRoom(Booking b, Unit r) {
		assignmentUnitBooking.put(r, b);
	}

	/**
	 * Get the root element from the tree structure. The whole tree structure is
	 * linked with this root so you get the whole tree.
	 * 
	 * @return
	 * 		the root element
	 */
	public TreeDataModel getRoot() {
		return root;
	}

	/**
	 * Set the tree root element
	 * @param root
	 */
	public void setRoot(TreeDataModel root) {
		this.root = root;
	}
	
	/**
	 *	Get the list with all receipts
	 * @return
	 * 		receipt list
	 */
	public List<Receipt> getAllReceipts() {
		return allReceipts;
	}

	/**
	 * Add a new receipt to the list
	 * @param receipts
	 * 		new element to add to the list
	 */
	public void addReceipts(Receipt receipts) {
		allReceipts.add(receipts);
	}

}
