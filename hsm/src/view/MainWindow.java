package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollBar;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MainWindow {

	private JFrame frmHousingManagementSystem;
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldStreet;
	private JTextField textFieldCity;
	private JTextField textFieldZipCode;
	private JTextField textFieldBirthday;
	private JTextField textFieldCardHoldersName;
	private JTextField textFieldCreditCardNumber;
	private JTextField textFieldCVVCode;
	private JTextField textFieldExpieringDate;
	private JTextField textFieldNameOnCard;
	private JTextField textFieldAccountNumber;
	private JTextField textFieldBankNumber;
	private JPanel panelCards;
	private JComboBox<String> comboBox;
	private JPanel panelPayMethod;
	
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

		JPanel panel = new JPanel();
		mainPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards,"Home");
			}
		});
		btnHome.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnHome);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut);
		
		JButton btnCheckIn = new JButton("Check-In");
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards,"CheckIn");
			}
		});
		panel.add(btnCheckIn);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);
		
		JButton btnCheckout = new JButton("Check-Out");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards,"CheckOut");
			}
		});
		panel.add(btnCheckout);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_2);
		
		JButton btnOverview = new JButton("Overview");
		btnOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards,"Overview");
			}
		});
		panel.add(btnOverview);
		
		JTree tree = new JTree();
		mainPanel.add(tree, BorderLayout.WEST);
		
		panelCards = new JPanel();
		mainPanel.add(panelCards, BorderLayout.CENTER);
		panelCards.setLayout(new CardLayout(0, 0));
		
		JList list = new JList();
		panelCards.add(list, "Home");
		
		JPanel panelCheckOut = new JPanel();
		panelCards.add(panelCheckOut, "CheckOut");
		
		JPanel panelOverview = new JPanel();
		panelCards.add(panelOverview, "Overview");
		
		JPanel panelCheckIn = new JPanel();
		panelCards.add(panelCheckIn, "CheckIn");
		panelCheckIn.setBackground(SystemColor.inactiveCaption);
		panelCheckIn.setLayout(null);
		
		JLabel lblFirstname = new JLabel("Firstname:");
		lblFirstname.setBounds(40, 75, 69, 14);
		panelCheckIn.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname:");
		lblLastname.setBounds(40, 125, 69, 14);
		panelCheckIn.add(lblLastname);
		
		JLabel lblAdress = new JLabel("Adress:");
		lblAdress.setBounds(40, 175, 46, 14);
		panelCheckIn.add(lblAdress);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(40, 225, 46, 14);
		panelCheckIn.add(lblCity);
		
		JLabel lblZipcode = new JLabel("ZipCode:");
		lblZipcode.setBounds(40, 275, 46, 14);
		panelCheckIn.add(lblZipcode);
		
		JLabel lblBirthday = new JLabel("Birthday:");
		lblBirthday.setBounds(40, 325, 46, 14);
		panelCheckIn.add(lblBirthday);
		
		JLabel lblPaymethod = new JLabel("Paymethod:");
		lblPaymethod.setBounds(40, 375, 69, 14);
		panelCheckIn.add(lblPaymethod);
		
		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(107, 70, 142, 25);
		panelCheckIn.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(107, 120, 142, 25);
		panelCheckIn.add(textFieldLastName);
		textFieldLastName.setColumns(10);
		
		textFieldStreet = new JTextField();
		textFieldStreet.setBounds(107, 170, 142, 25);
		panelCheckIn.add(textFieldStreet);
		textFieldStreet.setColumns(10);
		
		textFieldCity = new JTextField();
		textFieldCity.setBounds(107, 220, 142, 25);
		panelCheckIn.add(textFieldCity);
		textFieldCity.setColumns(10);
		
		textFieldZipCode = new JTextField();
		textFieldZipCode.setBounds(107, 270, 142, 25);
		panelCheckIn.add(textFieldZipCode);
		textFieldZipCode.setColumns(10);
		
		textFieldBirthday = new JTextField();
		textFieldBirthday.setBounds(107, 320, 142, 25);
		panelCheckIn.add(textFieldBirthday);
		textFieldBirthday.setColumns(10);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Credit Card", "Debit Card"}));
		comboBox.setBounds(107, 370, 142, 25);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelPayMethod.getLayout();
				if (comboBox.getSelectedIndex()==0) {
					c1.show(panelPayMethod, "CreditCard");
				}
				else {
					c1.show(panelPayMethod, "DebitCard");
				}
			}
		});
		panelCheckIn.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(SystemColor.desktop);
		separator.setForeground(SystemColor.desktop);
		separator.setBounds(312, 11, 10, 492);
		panelCheckIn.add(separator);
		
		JLabel lblPersonalData = new JLabel("Personal Data");
		lblPersonalData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPersonalData.setBounds(40, 25, 153, 14);
		panelCheckIn.add(lblPersonalData);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(586, 480, 89, 23);
		panelCheckIn.add(btnSave);
		
		panelPayMethod = new JPanel();
		panelPayMethod.setBounds(332, 72, 343, 317);
		panelCheckIn.add(panelPayMethod);
		panelPayMethod.setLayout(new CardLayout(0, 0));
		
		JPanel panelCreditCard = new JPanel();
		panelPayMethod.add(panelCreditCard, "CreditCard");
		panelCreditCard.setLayout(null);
		
		JLabel lblCardHoldersName = new JLabel("Card Holders Name:");
		lblCardHoldersName.setBounds(10, 50, 104, 14);
		panelCreditCard.add(lblCardHoldersName);
		
		JLabel lblCreditCardNumber = new JLabel("Credit Card Number:");
		lblCreditCardNumber.setBounds(10, 100, 104, 14);
		panelCreditCard.add(lblCreditCardNumber);
		
		JLabel lblCvvCode = new JLabel("CVV Code:");
		lblCvvCode.setBounds(10, 150, 104, 14);
		panelCreditCard.add(lblCvvCode);
		
		JLabel lblExperingDate = new JLabel("Expering Date:");
		lblExperingDate.setBounds(10, 200, 104, 14);
		panelCreditCard.add(lblExperingDate);
		
		textFieldCardHoldersName = new JTextField();
		textFieldCardHoldersName.setBounds(124, 45, 150, 25);
		panelCreditCard.add(textFieldCardHoldersName);
		textFieldCardHoldersName.setColumns(10);
		
		textFieldCreditCardNumber = new JTextField();
		textFieldCreditCardNumber.setBounds(124, 95, 150, 25);
		panelCreditCard.add(textFieldCreditCardNumber);
		textFieldCreditCardNumber.setColumns(10);
		
		textFieldCVVCode = new JTextField();
		textFieldCVVCode.setBounds(124, 145, 150, 25);
		panelCreditCard.add(textFieldCVVCode);
		textFieldCVVCode.setColumns(10);
		
		textFieldExpieringDate = new JTextField();
		textFieldExpieringDate.setBounds(124, 195, 150, 25);
		panelCreditCard.add(textFieldExpieringDate);
		textFieldExpieringDate.setColumns(10);
		
		JLabel lblCreditCardInformation = new JLabel("Credit Card Information");
		lblCreditCardInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCreditCardInformation.setBounds(10, 11, 209, 14);
		panelCreditCard.add(lblCreditCardInformation);
		
		JPanel panelDebitCard = new JPanel();
		panelPayMethod.add(panelDebitCard, "DebitCard");
		panelDebitCard.setLayout(null);
		
		JLabel lblDebitCardInformation = new JLabel("Debit Card Information:");
		lblDebitCardInformation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDebitCardInformation.setBounds(10, 11, 226, 14);
		panelDebitCard.add(lblDebitCardInformation);
		
		JLabel lblNameOnCard = new JLabel("Name on Card:");
		lblNameOnCard.setBounds(10, 50, 104, 14);
		panelDebitCard.add(lblNameOnCard);
		
		JLabel lblAccountNumber = new JLabel("Account Number:");
		lblAccountNumber.setBounds(10, 100, 104, 14);
		panelDebitCard.add(lblAccountNumber);
		
		JLabel lblBankNumber = new JLabel("Bank Number:");
		lblBankNumber.setBounds(10, 150, 104, 14);
		panelDebitCard.add(lblBankNumber);
		
		textFieldNameOnCard = new JTextField();
		textFieldNameOnCard.setBounds(124, 45, 150, 25);
		panelDebitCard.add(textFieldNameOnCard);
		textFieldNameOnCard.setColumns(10);
		
		textFieldAccountNumber = new JTextField();
		textFieldAccountNumber.setBounds(124, 95, 150, 25);
		panelDebitCard.add(textFieldAccountNumber);
		textFieldAccountNumber.setColumns(10);
		
		textFieldBankNumber = new JTextField();
		textFieldBankNumber.setBounds(124, 145, 150, 25);
		panelDebitCard.add(textFieldBankNumber);
		textFieldBankNumber.setColumns(10);

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
