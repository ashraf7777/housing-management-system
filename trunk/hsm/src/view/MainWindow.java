package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;

import model.ExampleData;
import model.Unit;

public class MainWindow {

	private JFrame frmHousingManagementSystem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExampleData.loadSampleTreeData();
					MainWindow window = new MainWindow();
					window.frmHousingManagementSystem.setVisible(true);
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
		frmHousingManagementSystem = new JFrame();
		frmHousingManagementSystem.setForeground(Color.WHITE);
		frmHousingManagementSystem.setAlwaysOnTop(true);
		frmHousingManagementSystem.setBackground(Color.BLUE);
		frmHousingManagementSystem
				.setFont(new Font("Agency FB", Font.PLAIN, 12));
		frmHousingManagementSystem.setTitle("Housing Management System");
		frmHousingManagementSystem.setBounds(100, 100, 800, 600);
		frmHousingManagementSystem
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHousingManagementSystem.getContentPane().setLayout(
				new CardLayout(0, 0));
		frmHousingManagementSystem.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("images/Home.png"));

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				Color.LIGHT_GRAY, null));
		frmHousingManagementSystem.getContentPane().add(mainPanel,
				"name_4935076455183");
		mainPanel.setLayout(new BorderLayout(0, 0));
		try {
			UIManager
			.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JTree tree = new JTree(Unit.getTree());
		mainPanel.add(tree, BorderLayout.WEST);

		JList list = new JList();
		mainPanel.add(list, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		mainPanel.add(panel, BorderLayout.SOUTH);

		JButton btnTest = new JButton("Test");
		panel.add(btnTest);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		frmHousingManagementSystem.setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("File");
		menuFile.setIcon(null);
		menuFile.setForeground(Color.BLACK);
		menuFile.setBackground(Color.WHITE);
		menuBar.add(menuFile);

		JMenuItem menuItemClose = new JMenuItem("Close");
		menuItemClose.setIcon(new ImageIcon("images/Close-icon.png"));
		menuItemClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(menuItemClose);

		JMenu menuHelp = new JMenu("Help");
		menuHelp.setForeground(Color.BLACK);
		menuBar.add(menuHelp);

		JMenuItem menuItemManual = new JMenuItem("Manual");
		menuItemManual.setIcon(new ImageIcon("images/Manual.png"));
		menuHelp.add(menuItemManual);

		JMenuItem menuItemAbout = new JMenuItem("About");
		menuItemAbout.setIcon(new ImageIcon("images/about-us.png"));
		menuHelp.add(menuItemAbout);
	}

}
