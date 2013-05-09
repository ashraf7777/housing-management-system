package model;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private List<Booking> allBookings = new ArrayList<Booking>();  
	
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
}
