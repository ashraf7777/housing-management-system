package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.GUI_handler;

/**
 * This class creates a sample structure of units and executes sample bookings.
 * 
 * @author D20018
 * 
 */

public class ExampleData {

	private Model model;

	/**
	 * Add the first and second tier to the structure
	 */
	public void loadSampleTreeData() {
		TreeDataModel[][] units = new TreeDataModel[5][15];
		// First tier
		TreeDataModel root = new TreeDataModel(new Unit("University Village"));
		// Store the root in the model
		model.setRoot(root);

		// Add lower tier structure elements
		units[0][0] = new TreeDataModel(new Unit("London"));
		root.add(units[0][0]);
		addApartments(units[0][0], 4);

		units[0][1] = new TreeDataModel(new Unit("Sydney"));
		root.add(units[0][1]);
		addApartments(units[0][1], 3);

		units[0][2] = new TreeDataModel(new Unit("Madrid"));
		root.add(units[0][2]);
		addApartments(units[0][2], 2);

		units[0][3] = new TreeDataModel(new Unit("Paris"));
		root.add(units[0][3]);
		addApartments(units[0][3], 3);

		units[0][4] = new TreeDataModel(new Unit("Tokyo"));
		root.add(units[0][4]);
		addApartments(units[0][4], 4);
	}

	/**
	 * Add apartments to the provided unit (building)
	 * 
	 * @param u
	 *            superiour unit
	 * @param number
	 *            number of apartments to be added
	 */
	private void addApartments(TreeDataModel u, int number) {
		TreeDataModel u2;
		// Add the defined number of apartments to the building
		for (int i = 1; i <= number; i++) {
			// Create a new apartment
			u2 = new TreeDataModel(new Unit((Integer.toString(i))));
			// Add the apartment to the building
			u.add(u2);
			// Set the superiour unit
			((Unit) u2.getUserObject()).setSuperiorUnit((Unit) u
					.getUserObject());
			// Add rooms to the apartment
			addRooms(u2);
		}
	}

	/**
	 * Add rooms to the provided unit (apartment)
	 * 
	 * @param u
	 *            superiour unit
	 */
	private void addRooms(TreeDataModel u) {
		Unit u3 = new Unit("Normal", 100, 32.5f, 975f);
		u3.setSuperiorUnit((Unit) u.getUserObject());
		u.add(new TreeDataModel(u3));
		u3 = new Unit("Large", 100, 33.2f, 995f);
		u3.setSuperiorUnit((Unit) u.getUserObject());
		u.add(new TreeDataModel(u3));
		u3 = new Unit("Deluxe", 100, 35.9f, 1075f);
		u3.setSuperiorUnit((Unit) u.getUserObject());
		u.add(new TreeDataModel(u3));
	}

	/**
	 * Excecute sample bookings. Tenants names are randomly choosen from a given
	 * sample list.
	 * 
	 * @param number
	 *            number of sample bookings to be executed
	 * @param my_handler
	 *            handler object is needed to use some functions
	 */
	public void loadSampleBookings(int number, GUI_handler my_handler) {
		// sample names
		String[] firstNames = { "Mia", "Emma", "Hanna", "Lea", "Sophia",
				"Lena", "Leonie", "Lina", "Ben", "Luca", "Paul", "Lucas",
				"Finn", "Jonas", "Leon", "Felix", "Tim", "Max" };
		String[] lastNames = { "Mueller", "Schmidt", "Schneider", "Fischer",
				"Meyer" };

		Payment paymentTyp = null;
		String firstName, lastName, street, city, zipCode;
		Unit userObject = null;
		Booking b = null;
		DefaultMutableTreeNode tree;
		int numberOfPersons = 1;
		Date birthday = null;

		try {
			// Set a birthday for the sample persons
			birthday = new SimpleDateFormat("MM.dd.yyyy").parse("12.17.1991");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		;

		// Own address
		street = "2004 Oxford Ave. 9";
		city = "Fullerton";
		zipCode = "92831";

		// Loop for creating the sample bookings
		for (int i = 0; i < number; i++) {
			// Get a randomly choosen first and last name
			firstName = firstNames[getRandomIndex(firstNames.length)];
			lastName = lastNames[getRandomIndex(lastNames.length)];

			// Get the tree where just the free rooms are included
			tree = my_handler.showCheckInTree();
			// If the tree element has no children there are no free rooms left
			if (tree.isLeaf()) {
				JOptionPane.showMessageDialog(null, "No rooms are left",
						"Booked Out", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				// Get a random unit of the free rooms
				userObject = getRandomUnit(tree);
			}

			// Choose randomly a paymentType
			if (Math.random() * 10 >= 5) {
				paymentTyp = new DebitCard(firstName + " " + lastName,
						"Account Number", "Bank number", "Name of bank");
				paymentTyp.setName("Debit Card");
			} else {
				paymentTyp = new CreditCard(firstName + " " + lastName,
						"Credit card number", "CVV", "Expiering Date");
				paymentTyp.setName("Credit Card");
			}
			// Excecute the booking with the previously setted data
			b = new Booking(firstName, lastName, birthday, street, city,
					zipCode, numberOfPersons, userObject, new Date(
							System.currentTimeMillis()), paymentTyp);
			// Store the data in the model
			model.addBookingToRoom(b, userObject);
			model.addBookingToList(b);
			userObject.addBooking(b);
			userObject.setOccupied(true);
		}
	}

	/**
	 * This function chooses randomly a room out of the provided tree structure
	 * 
	 * @param tree
	 *            provided tree structure with free rooms
	 * @return randomly choosen free room
	 */
	private Unit getRandomUnit(DefaultMutableTreeNode tree) {
		// As long as the actual unit element is not a room go a tier deeper
		// If it is the last tier (room) return the object
		while (!tree.isLeaf()) {
			// Choose randomly an element of the actual tier
			tree = (DefaultMutableTreeNode) tree.getChildAt(getRandomIndex(tree
					.getChildCount()));
		}
		return (Unit) tree.getUserObject();
	}

	/**
	 * This function provides this class with random numbers. The range is
	 * between 0 and i-1
	 * 
	 * @param i
	 * @return
	 */
	private int getRandomIndex(int i) {
		int index;
		do {
			index = (int) (Math.random() * 10);
		} while (index >= i);
		return index;
	}

	/**
	 * Announce the model object to use its functions
	 * 
	 * @param model
	 */
	public void announceModel(Model model) {
		this.model = model;
	}
}
