package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

import controller.GUI_handler;

/**
 * This class creates a sample structure of units and executes sample bookings.
 * @author D20018
 *
 */

public class ExampleData {

	private Model model;
	
	/**
	 * Add the first and second tier to the structure
	 */
	public void loadSampleTreeData()
	{
		TreeDataModel[][] units = new TreeDataModel[5][15];
		TreeDataModel root = new TreeDataModel( new Unit("University Village"));
		model.setRoot(root);
		
		units[0][0] = new TreeDataModel (new Unit("London"));
		root.add(units[0][0]);
		addApartments(units[0][0], 4);
		
		units[0][1] = new TreeDataModel (new Unit("Sydney"));
		root.add(units[0][1]);
		addApartments(units[0][1], 3);
		
		units[0][2] = new TreeDataModel (new Unit("Madrid"));
		root.add(units[0][2]);
		addApartments(units[0][2], 2);
		
		units[0][3] = new TreeDataModel (new Unit("Paris"));
		root.add(units[0][3]);
		addApartments(units[0][3], 3);		
		
		units[0][4] = new TreeDataModel (new Unit("Tokyo"));
		root.add(units[0][4]);
		addApartments(units[0][4], 4);	
	}
	
	/**
	 * Add apartments to the provided unit (building)
	 * @param u
	 * 			superiour unit
	 * @param number
	 * 			number of apartments to be added
	 */
	private void addApartments(TreeDataModel u, int number)
	{
		TreeDataModel u2;
		for (int i = 1; i <= number; i++)
			{
				u2 = new TreeDataModel( new Unit((Integer.toString(i))));
				u.add(u2);
				((Unit)u2.getUserObject()).setSuperiorUnit((Unit)u.getUserObject());
				addRooms(u2);
			}
	}
	
	/**
	 * Add rooms to the provided unit (apartment)
	 * @param u
	 * 			superiour unit
	 */
	private void addRooms(TreeDataModel u)
	{
		Unit u3 = new Unit("Normal", 100, 32.5f, 975f);
		u3.setSuperiorUnit((Unit)u.getUserObject());
		u.add(new TreeDataModel(u3));
		u3 = new Unit("Large", 100, 33.2f, 995f);
		u3.setSuperiorUnit((Unit)u.getUserObject());
		u.add(new TreeDataModel(u3));
		u3 = new Unit("Deluxe", 100, 35.9f, 1075f);
		u3.setSuperiorUnit((Unit)u.getUserObject());
		u.add(new TreeDataModel(u3));
	}
	
	/**
	 * Excecute sample 
	 * @param number
	 * @param my_handler
	 */
	public void loadSampleBookings(int number, GUI_handler my_handler)
	{
		String[] firstNames = {"Mia", "Emma", "Hanna", "Lea", "Sophia", "Lena", "Leonie", "Lina", "Ben",
				"Luca", "Paul", "Lucas", "Finn", "Jonas", "Leon", "Felix", "Tim", "Max"};
		String[] lastNames = {"Mueller", "Schmidt", "Schneider", "Fischer", "Meyer"};
		
		Payment paymentTyp = null;
		String firstName, lastName, street, city, zipCode;
		Unit userObject = null;
		Booking b = null;
		DefaultMutableTreeNode tree;
		int numberOfPersons = 1;
		Date birthday = null;
		
		try {
			birthday = new SimpleDateFormat("MM.dd.yyyy").parse("12.17.1991");
		} catch (ParseException e) {
			e.printStackTrace();
		};
		street = "2004 Oxford Ave. 9";
		city = "Fullerton";
		zipCode = "92831";
		
		for(int i = 0; i < number; i++)
		{
			firstName = firstNames[getRandomIndex(firstNames.length)];
			lastName = lastNames[getRandomIndex(lastNames.length)];
			
			tree = my_handler.showCheckInTree();
			if (tree.isLeaf())							//If the tree element has no children there are no free rooms left
			{
				//TODO: Fehlermeldung
			}
			else
			{
				userObject = getRandomUnit(my_handler.showCheckInTree());
			}
			
			if (Math.random() * 10 >= 5)
			{
				paymentTyp = new DebitCard(firstName + " " + lastName,
						"Account Number",
						"Bank number", "Name of bank");
				paymentTyp.setName("Debit Card");
			}
			else
			{
				paymentTyp = new CreditCard(firstName + " " + lastName, "Credit card number",
						"CVV", "Expiering Date");
				paymentTyp.setName("Credit Card");
			}
			b = new Booking(firstName, lastName, birthday, street, city, zipCode, 
					numberOfPersons, userObject, new Date(System.currentTimeMillis()), paymentTyp);
			model.addBookingToRoom(b, userObject);
			model.addBookingToList(b);
			userObject.addBooking(b);
			userObject.setOccupied(true);
		}
	}
	

	private Unit getRandomUnit(DefaultMutableTreeNode tree)
	{
		int i = 0;
		while(!tree.isLeaf())
		{
			do
			{
				i = (int)(Math.random()* 10);
			}
			while(i >= tree.getChildCount());
			tree = (DefaultMutableTreeNode)tree.getChildAt(i);
		}
		return (Unit) tree.getUserObject();
	}
	
	private int getRandomIndex(int i)
	{
		int index;
		do
		{
			index = (int)(Math.random()* 10);
		}
		while(index >= i);
		return index;
	}
	
	public void announceModel(Model model)
	{
		this.model = model;
	}
}
