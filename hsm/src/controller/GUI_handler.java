package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

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

	public DefaultMutableTreeNode showCheckInTree() {

		return buildTreeCheckInAndOut(true);
	}

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

	public void commitCheckOut() {
		if (null != gui.getAusgewaehlterKnoten()) {
			Unit room = (Unit) gui.getAusgewaehlterKnoten().getUserObject();
			if (!room.isOccupied()) {
				// TODO: Fehlermeldung: Falscher Knoten ausgewählt. Raum ist
				// nicht belegt
			} else {
				Date moveInDate = model.getBookingFromRoom(room)
						.getCheckInDate();
				Date moveOutDate = new Date(System.currentTimeMillis());
				Date timeToPay = new Date(moveOutDate.getTime()
						- moveInDate.getTime());

				int years = timeToPay.getYear();
				int months = timeToPay.getMonth();
				int days = timeToPay.getDay();

				float total = years * 12 * room.getPricePerMonth();
				total = total + months * room.getPricePerMonth() + days
						* room.getPricePerNight();

				System.out.println("Total costs: " + total);

			}
		} else {
			// TODO: Fehlermeldung: Falschr Hierarchieebene ausgewählt
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
									new_room = new TreeDataModel(
											(Unit) room.getUserObject());

									if (!isNodeAlreadyAdded(new_building,
											newTree)) // If the building isn't
														// yet added to the new
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
		if (newTree.getChildCount() == 0)
			return new DefaultMutableTreeNode("No rooms available");
		return newTree;
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
