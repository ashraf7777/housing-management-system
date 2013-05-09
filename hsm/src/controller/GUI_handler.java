package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Booking;
import model.Model;
import model.Unit;

public class GUI_handler implements ActionListener {
	
	
	private Model model;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().toLowerCase().equals(("Home").toLowerCase())) {
			showHome();
		} else if (e.getActionCommand().toLowerCase().equals(("Check-In").toLowerCase())) {
			checkIn();	
		} else if (e.getActionCommand().toLowerCase().equals(("Check-Out").toLowerCase())) {
			checkOut();	
		} else if (e.getActionCommand().toLowerCase().equals(("Overview").toLowerCase())) {
			showOverview();
		}
		
	}
	
	
	
	
	
	
	private void showHome()
	{
		
	}
	
	
	private void checkIn()
	{
		//TODO: show Check-In Menu
		Unit freeRoomsTree;
		freeRoomsTree = (Unit)Unit.getTree().getRoot();
		
		Unit root = Unit.getTree();
		Unit building, apartment, room;
		for (int j = 0; j < root.getChildCount(); j++) {
			if (root.getChildAt(j).isLeaf()) {
				building = (Unit)root.getChildAt(j);
				for(int i = 0; i < building.getChildCount(); i ++)
				{
					if(building.getChildAt(i).isLeaf())
					{
						apartment = (Unit)building.getChildAt(i);
						for(int k = 0 ; k < apartment.getChildCount(); k++)
						{
							if (apartment.getChildAt(k).isLeaf())
							{
								room = (Unit) apartment.getChildAt(k);
								if (!room.isOccupied())						//If the room is free it will be added to the new tree
								{
										if (!isNodeAlreadyAdded(building, freeRoomsTree)) //If the building isn't yet added to the new tree 
										{
											freeRoomsTree.add(building);
										}
										
										if (!isNodeAlreadyAdded(apartment, building))
										{
											building.add(apartment);
										}
										if (!isNodeAlreadyAdded(room, apartment))
										{
											apartment.add(room);
										}
								}
							}
						}
				}
				
			}
			}
		}
		
		
		//TODO: Tree in der Ansicht aktualisieren
	}
	
	private boolean isNodeAlreadyAdded(Unit node, Unit topNode)
	{
		return true;
	}
	
	private void checkOut()
	{
		
	}
	
	private void showOverview()
	{
		
		//TODO: Ansicht wechseln
		
		List<Booking> bookings = model.getAllBookings();
		for(int i = 0 ; i < bookings.size(); i++)
		{
			//
		}
		
	}
	
	public void announceModel(Model model)
	{
		this.model = model;
	}
	
	

}
