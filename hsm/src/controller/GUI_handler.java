package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.tree.DefaultMutableTreeNode;

import model.Unit;

public class GUI_handler implements ActionListener {

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
		//TODO: Tree neu aufbauen und nur die freien Räume und Apartments anzeigen
		Unit freeRoomsTree;
		
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
								if (!room.isOccupied())
								{
									//Hinzufügen der einzelen Knoten zum freeRoomsTree. 
									//Nicht jedes mal das building und das apartment hinzufügen
								}
							}
						}
				}
				
			}
			}
		}
	}
	
	private void checkOut()
	{
		
	}
	
	private void showOverview()
	{
		
	}
	
	

}
