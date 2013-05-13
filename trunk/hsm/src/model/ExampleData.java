package model;

import model.Model;

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
	
	private static void addApartments(TreeDataModel u, int number)
	{
		TreeDataModel u2;
		for (int i = 1; i <= number; i++)
			{
				u2 = new TreeDataModel( new Unit((Integer.toString(i))));
				u.add(u2);
				addRooms(u2);
			}
	}
	
	
	private static void addRooms(TreeDataModel u)
	{
		u.add(new TreeDataModel(new Unit("Normal", 100, 32.5f, 975f)));
		u.add(new TreeDataModel(new Unit("Large", 100, 32.5f, 975f)));
		u.add(new TreeDataModel(new Unit("Deluxe", 100, 32.5f, 975f)));
	}
	
	
	public void announceModel(Model model)
	{
		this.model = model;
	}
}
