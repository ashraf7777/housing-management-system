package model;

public class ExampleData {

	public static void loadSampleTreeData()
	{
		Unit[][] units = new Unit[5][15];
		Unit root = Unit.addRoot("University Village");
		
		units[0][0] = root.addTreeElement("London");
		addApartments(units[0][0], 4);
		
		units[0][1] = root.addTreeElement("Sydney");
		addApartments(units[0][1], 3);
		
		units[0][2] = root.addTreeElement("Madrid");
		addApartments(units[0][2], 2);
		
		units[0][3] = root.addTreeElement("Paris");
		addApartments(units[0][3], 3);		
		
		units[0][4] = root.addTreeElement("Tokyo");
		addApartments(units[0][4], 4);		
	}
	
	private static void addApartments(Unit u, int number)
	{
		Unit u2;
		for (int i = 1; i <= number; i++)
			{
				u2 = u.addTreeElement(Integer.toString(i));
				addRooms(u2);
			}
	}
	
	
	private static void addRooms(Unit u)
	{
		u.addTreeElement("Normal", 100, 32.5f, 975f);
		u.addTreeElement("Large", 150, 33.2f, 995f);
		u.addTreeElement("Deluxe", 200, 34.2f, 1025f);
	}
}
