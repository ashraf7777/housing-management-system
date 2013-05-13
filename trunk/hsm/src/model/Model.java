package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

	private List<Booking> allBookings = new ArrayList<Booking>();  
	private Map<Unit, Booking> assignmentUnitBooking = new HashMap<Unit, Booking>();
	private TreeDataModel root;
	
	public List<Booking> getAllBookings() {
		return allBookings;
	}

	public void addBookingToList(Booking b) {
		allBookings.add(b);
	}
	public void removeBookingFromList(Booking b)
	{
		allBookings.remove(b);
	}
	
	public void clearBookingList()
	{
		allBookings.clear();
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
	
	
	
}
