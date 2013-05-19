package model;

import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

import controller.GUI_handler;


public class ExampleData {

	private Model model;
	
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
	
	private void addApartments(TreeDataModel u, int number)
	{
		TreeDataModel u2;
		for (int i = 1; i <= number; i++)
			{
				u2 = new TreeDataModel( new Unit((Integer.toString(i))));
				u.add(u2);
				addRooms(u2);
			}
	}
	
	
	private void addRooms(TreeDataModel u)
	{
		u.add(new TreeDataModel(new Unit("Normal", 100, 32.5f, 975f)));
		u.add(new TreeDataModel(new Unit("Large", 100, 33.2f, 995f)));
		u.add(new TreeDataModel(new Unit("Deluxe", 100, 35.9f, 1075f)));
	}
	
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
		int random = 0;
		
		Date birthday = null;
		street = "2004 Oxford Ave. 9";
		city = "Fullerton";
		zipCode = "92831";
		
		for(int i = 0; i < number; i++)
		{
			do
			{
				random = (int)(Math.random()* 10);
			}
			while(random >= firstNames.length);
			firstName = firstNames[random];
			
			do
			{
				random = (int)(Math.random() * 10);
			}
			while(random >= lastNames.length);
			lastName = lastNames[random];
			
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
			userObject.setOccupied(true);
		}
	}
	

	public Unit getRandomUnit(DefaultMutableTreeNode tree)
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
	
	public void announceModel(Model model)
	{
		this.model = model;
	}
}
