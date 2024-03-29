package main;

import model.ExampleData;
import model.Model;
import view.MainWindow;
import controller.GUI_handler;

/**
 * Main method of the application. This mehtod starts the application and
 * controls its beginning.
 * 
 * @author D20018
 * 
 */
public class HSM_System {

	public static void main(String[] args) {
		Model model = new Model();
		ExampleData exData = new ExampleData();
		exData.announceModel(model);
		exData.loadSampleTreeData();
		MainWindow window = new MainWindow(model);
		GUI_handler my_handler = new GUI_handler();

		window.announceHandler(my_handler);
		window.announceModel(model);
		my_handler.announceModel(model);
		my_handler.announceGui(window);
		exData.loadSampleBookings(5, my_handler);
	}

}
