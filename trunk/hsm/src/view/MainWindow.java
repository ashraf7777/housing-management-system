package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import javax.swing.ImageIcon;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, "name_4935076455183");
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuFile.setIcon(null);
		menuFile.setForeground(Color.BLACK);
		menuFile.setBackground(Color.WHITE);
		menuBar.add(menuFile);
		
		JMenuItem menuItemClose = new JMenuItem("Close");
		menuItemClose.setIcon(null);
		menuFile.add(menuItemClose);
		
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setForeground(Color.BLACK);
		menuBar.add(menuHelp);
		
		JMenuItem menuItemManual = new JMenuItem("Manual");
		menuHelp.add(menuItemManual);
		
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuHelp.add(menuItemAbout);
	}

}
