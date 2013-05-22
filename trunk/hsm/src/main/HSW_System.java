package main;

import model.ExampleData;
import model.Model;
import view.MainWindow;
import controller.GUI_handler;

public class HSW_System {

	public static void main(String[] args) {
		Model model = new Model();
		ExampleData exData = new ExampleData();
		exData.announceModel(model);
		exData.loadSampleTreeData();
		MainWindow window = new MainWindow(model);
		GUI_handler my_handler = new GUI_handler();

		window.announceHandler(my_handler, my_handler);
		window.announceModel(model);
		my_handler.announceModel(model);
		my_handler.announceGui(window);
		exData.loadSampleBookings(5, my_handler);

		
//		Receipt r = new Receipt();
//		r.createPdf();
	}

}
