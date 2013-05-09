package model;

import java.util.ArrayList;
import java.util.List;

public class Model {

	private List<Booking> allBookings = new ArrayList<Booking>();  
	
	public List<Booking> getAllBookings() {
		return allBookings;
	}



	public void addBookingToList(Booking b) {
		Booking.allBookings.add(b);
	}
	
	public void clearBookingList()
	{
		Booking.allBookings.clear();
	}
}
