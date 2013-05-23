package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Booking;
import model.Model;
import model.TreeDataModel;
import model.Unit;
import view.MainWindow;


/**
 * This class provides calculating and logic operations for the GUI.
 * @author D20018
 *
 */
public class GUI_handler implements ActionListener {

	private Model model;
	private MainWindow gui;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().toLowerCase().equals(("Home").toLowerCase())) {
			showHome();
		} else if (e.getActionCommand().toLowerCase()
				.equals(("Check-In").toLowerCase())) {
			// checkIn();
		} else if (e.getActionCommand().toLowerCase()
				.equals(("Check-Out").toLowerCase())) {
			// checkOut();
		} else if (e.getActionCommand().toLowerCase()
				.equals(("Overview").toLowerCase())) {
			showOverview();
		}

	}

	public DefaultMutableTreeNode showHome() {
		return model.getRoot();
	}

	/**
	 * Get all free rooms
	 * @return tree object
	 * 		retruns all free rooms in a tree structure
	 */
	public DefaultMutableTreeNode showCheckInTree() {

		return buildTreeCheckInAndOut(true);
	}

	/**
	 * Check if the node is already added to the top level node
	 * @param node
	 * 			node to be proved if it is already added to the superiour node
	 * @param topNode
	 * 			superiour node
	 * @return
	 * 			true if the node is already added
	 * 			false if the node isn't added to the superiour node
	 */
	private boolean isNodeAlreadyAdded(DefaultMutableTreeNode node,
			DefaultMutableTreeNode topNode) {
		for (int i = 0; i < topNode.getChildCount(); i++) {
			if (topNode.getChildAt(i).toString().equals(node.toString()))
				return true;
		}
		return false;
	}

	public DefaultMutableTreeNode showCheckOutTree() {

		return buildTreeCheckInAndOut(false);
	}

	/**
	 * Logic operations for the check out.
	 * Move out date will be setted and the costs are calculated
	 * @return
	 * 			true if the check out was successful
	 * 			false if the check out was NOT successful
	 */
	public boolean commitCheckOut() {
		//if no room was selected the check out can not be proceeded
		if (null != gui.getAusgewaehlterKnoten()) {
			//Load the room object out of the tree element object
			Unit room = (Unit) gui.getAusgewaehlterKnoten().getUserObject();
			//if the room is not occupied the check out can not be proceeded
			if (!room.isOccupied()) {
				JOptionPane
						.showMessageDialog(
								null,
								"You haven't choosen a correct room.\n The choosen unit is no room!",
								"Invalid room", JOptionPane.ERROR_MESSAGE);
				return false;
			} else {
				Booking booking = model.getBookingFromRoom(room);

				Date moveInDate = booking.getCheckInDate();
				Date moveOutDate = new Date(System.currentTimeMillis());
				booking.setCheckOutDate(moveOutDate);

				Date timeToPay = new Date(moveOutDate.getTime()
						- moveInDate.getTime());

				int years = (int) (timeToPay.getTime() / 1000 / 60 / 60 / 24 / 365);
				int months = (int) (timeToPay.getTime() / 1000 / 60 / 60 / 24 % 365) * 365 / 12;
				int days = (int) (timeToPay.getTime() / 1000 / 60 / 60 / 24 % 365) * 365 % 12 * 12;

				if (years == 0 && months == 0 && days == 0)
					days++;

				float total = years * 12 * room.getPricePerMonth();
				total = total + months * room.getPricePerMonth() + days
						* room.getPricePerNight();
				booking.setTotalCosts(total);

				room.setOccupied(false);
				return true;
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Please select a room for Check-Out!", "No room selected",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private DefaultMutableTreeNode buildTreeCheckInAndOut(boolean isCheckIn) {
		DefaultMutableTreeNode newTree;
		TreeDataModel normalTree = model.getRoot();
		newTree = new TreeDataModel((Unit) normalTree.getUserObject()); // Set
																		// the
																		// same
																		// root
																		// element
		DefaultMutableTreeNode building, apartment, room;
		DefaultMutableTreeNode new_building, new_apartment, new_room;
		for (int j = 0; j < normalTree.getChildCount(); j++) {
			if (!normalTree.getChildAt(j).isLeaf()) {
				building = (DefaultMutableTreeNode) normalTree.getChildAt(j);
				new_building = new TreeDataModel(
						(Unit) building.getUserObject());
				for (int i = 0; i < building.getChildCount(); i++) {
					if (!building.getChildAt(i).isLeaf()) {
						apartment = (DefaultMutableTreeNode) building
								.getChildAt(i);
						new_apartment = new TreeDataModel(
								(Unit) apartment.getUserObject());
						for (int k = 0; k < apartment.getChildCount(); k++) {
							if (apartment.getChildAt(k).isLeaf()) {
								room = (DefaultMutableTreeNode) apartment
										.getChildAt(k);
								if (isCheckIn
										|| ((Unit) room.getUserObject())
												.isOccupied()) // If the room is
																// free it will
																// be addedto
																// the new tree
								{
									// If we need the check-in tree the occupied
									// rooms should not be considered
									if (!(isCheckIn && ((Unit) room
											.getUserObject()).isOccupied())) {
										new_room = new TreeDataModel(
												(Unit) room.getUserObject());

										if (!isNodeAlreadyAdded(new_building,
												newTree)) // If the building
															// isn't
															// yet added to the
															// new
															// tree
										{
											newTree.add(new_building);
										}

										if (!isNodeAlreadyAdded(new_apartment,
												new_building)) {
											new_building.add(new_apartment);
										}
										if (!isNodeAlreadyAdded(new_room,
												new_apartment)) {
											new_apartment.add(new_room);
										}
									}
								}
							}
						}
					}

				}
			}
		}
		if (newTree.getChildCount() == 0)
			return new DefaultMutableTreeNode("No rooms available");
		return newTree;
	}

	/**
	 * Search for a lastname if there are any bookings available. This function
	 * provides also to search instantly during the typing of the user
	 * 
	 * @param lastName
	 * @return
	 */
	public List<Booking> searchName(String lastName) {
		List<Booking> bookings = model.getAllBookings();
		List<Booking> results = new ArrayList<Booking>();
		if (lastName.equals("")) {
			return results;
		} else {
			for (int i = 0; i < bookings.size(); i++) {
				if (bookings.get(i).getLastNameOfBooker().toLowerCase()
						.startsWith(lastName.toLowerCase())) {
					results.add(bookings.get(i));
				}
			}
			return results;
		}
		

	}

	private void showOverview() {

		// TODO: Ansicht wechseln

		List<Booking> bookings = model.getAllBookings();
		for (int i = 0; i < bookings.size(); i++) {
			//
		}

	}

	public void announceGui(MainWindow gui) {
		this.gui = gui;
	}

	public void announceModel(Model model) {
		this.model = model;
	}
}
