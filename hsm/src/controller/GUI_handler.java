package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import model.Booking;
import model.Model;
import model.TreeDataModel;
import model.Unit;
import view.MainWindow;

public class GUI_handler implements ActionListener {
	
	
	private Model model;
	private MainWindow gui;
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().toLowerCase().equals(("Home").toLowerCase())) {
			showHome();
		} else if (e.getActionCommand().toLowerCase().equals(("Check-In").toLowerCase())) {
		//	checkIn();	
		} else if (e.getActionCommand().toLowerCase().equals(("Check-Out").toLowerCase())) {
			//checkOut();	
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
		TreeDataModel normalTree;
		DefaultMutableTreeNode newTree;
		normalTree = model.getRoot(); 
		newTree = new TreeDataModel( (Unit)normalTree.getUserObject());		//Set the same root element
		DefaultMutableTreeNode building, apartment, room;
		for (int j = 0; j < normalTree.getChildCount(); j++) {
			if (normalTree.getChildAt(j).isLeaf()) {
				building = (DefaultMutableTreeNode) normalTree.getChildAt(j);
				for(int i = 0; i < building.getChildCount(); i ++)
				{
					if(building.getChildAt(i).isLeaf())
					{
						apartment = (DefaultMutableTreeNode) building.getChildAt(i);
						for(int k = 0 ; k < apartment.getChildCount(); k++)
						{
							if (apartment.getChildAt(k).isLeaf())
							{
								room = (DefaultMutableTreeNode) apartment.getChildAt(k);
								if ( !((Unit)room.getUserObject()).isOccupied())						//If the room is free it will be added to the new tree
								{
									System.out.println(((Unit)room.getUserObject()).isOccupied());
										if (!isNodeAlreadyAdded(building, newTree)) //If the building isn't yet added to the new tree 
										{
											newTree.add(new TreeDataModel((Unit)building.getUserObject()));
										}
										
										if (!isNodeAlreadyAdded(apartment, building))
										{
											building.add(new TreeDataModel((Unit)apartment.getUserObject()));
										}
										if (!isNodeAlreadyAdded(room, apartment))
										{
											apartment.add(new TreeDataModel((Unit)room.getUserObject()));
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
	
	private boolean isNodeAlreadyAdded(DefaultMutableTreeNode node, DefaultMutableTreeNode topNode)
	{
		return true;
	}
	
	private void checkOut()
	{
		if (null != gui.getAusgewaehlterKnoten())
		{
			DefaultMutableTreeNode room = gui.getAusgewaehlterKnoten();
			if (room.getChildCount() == 0)
			{
				Unit r = ((Unit)(room.getUserObject())); 
				if (r.isOccupied())
				{
					Date moveInDate = model.getBookingFromRoom(r).getCheckInDate();
					Date moveOutDate = new Date(System.currentTimeMillis());
				}
				else
				{
					//TODO: Fehlermeldung: Falscher Knoten ausgewählt. Raum ist nicht belegt
				}
			}
			else
			{
				//TODO: Fehlermeldung: Falschr Hierarchieebene ausgewählt 
			}
		}
		//No node is selected
		else
		{
			//TODO:Fehlermeldung: Kein Raum ausgewählt
		}
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
	
	public void announceGui(MainWindow gui)
	{
		this.gui = gui;
	}
	
	public void announceModel(Model model)
	{
		this.model = model;
	}
}
