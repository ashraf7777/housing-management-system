package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.text.SimpleDateFormat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.itextpdf.text.DocumentException;

import model.Booking;
import model.CreditCard;
import model.DebitCard;
import model.ExampleData;
import model.Model;
import model.Payment;
import model.Receipt;
import model.Unit;
import controller.Eventlistener;
import controller.GUI_handler;

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
	private JTable tableOverview;
	private JTable tableHome;
	private JTree tree;
	private int cardNumber;
	private Model model;
	private GUI_handler g_handler; // muss wieder gelöscht werden!!!!!!
	private JTextField textFieldNameOfBank;
	private JTable tableCheckOut;
	private DefaultTableModel tableCheckOutModel;

	private String[] columNames = { "Building", "Apartment", "Room",
			"Lastname", "Firstname", "Check-In Date", "Paymenttype", "Birthday" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
	public MainWindow(Model model) {
		this.model = model;
		initialize();
		this.frmHousingManagementSystem.setVisible(true);
	}

	public void announceHandler(ActionListener handler, GUI_handler g_handler) {
		this.g_handler = g_handler;

	}

	public void announceModel(Model model) {
		this.model = model;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frmHousingManagementSystem = new JFrame();
		frmHousingManagementSystem.setForeground(Color.WHITE);
		frmHousingManagementSystem.setAlwaysOnTop(false);
		frmHousingManagementSystem.setBackground(Color.BLUE);
		frmHousingManagementSystem
				.setFont(new Font("Agency FB", Font.PLAIN, 12));
		frmHousingManagementSystem.setTitle("Housing Management System");
		frmHousingManagementSystem.setBounds(100, 100, 900, 600);
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
				c1.show(panelCards, "Home");
				cardNumber = 1;
				TreeModel treeModel = new DefaultTreeModel(g_handler.showHome());
				tree.setModel(treeModel);
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
				c1.show(panelCards, "CheckIn");
				cardNumber = 2;
				TreeModel treeModel = new DefaultTreeModel(g_handler
						.showCheckInTree());
				tree.setModel(treeModel);
				// Aufruf der Check In Methode
			}
		});
		panel.add(btnCheckIn);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut_1);

		JButton btnCheckout = new JButton("Check-Out");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "CheckOut");
				cardNumber = 3;
				TreeModel treeModel2 = new DefaultTreeModel(g_handler
						.showCheckOutTree());
				tree.setModel(treeModel2);

			}
		});
		panel.add(btnCheckout);

		// Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		// panel.add(horizontalStrut_2);

		JButton btnOverview = new JButton("Overview");
		btnOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "Overview");
				cardNumber = 4;
			}
		});
		panel.add(btnOverview);

		tree = new JTree(model.getRoot()); // model.getRoot()
		mainPanel.add(tree, BorderLayout.WEST);
		tree.addTreeSelectionListener(new Eventlistener() {
			public void valueChanged(TreeSelectionEvent treeEvent) {
				DefaultMutableTreeNode treeNode;
				treeNode = getAusgewaehlterKnoten();
				if (null != treeNode) {
					if (cardNumber == 3) {

						ArrayList<Object[]> list = new ArrayList<>();
						getBuchungen(treeNode, list);

						Object[][] data = new Object[list.size()][];
						for (int i = 0; i < list.size(); i++) {
							data[i] = list.get(i);
						}
						tableCheckOutModel.setDataVector(data, columNames);
						tableCheckOut.updateUI();
					}
				}
			}
		});

		panelCards = new JPanel();
		mainPanel.add(panelCards, BorderLayout.CENTER);
		panelCards.setLayout(new CardLayout(0, 0));

		JPanel panelHome = new JPanel();
		panelCards.add(panelHome, "Home");

		tableHome = new JTable();
		panelHome.add(tableHome);

		JPanel panelOverview = new JPanel();
		panelCards.add(panelOverview, "Overview");
		panelOverview.setLayout(new BorderLayout(0, 0));

		tableOverview = new JTable();
		tableOverview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableOverview.setBackground(Color.WHITE);
		tableOverview.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableOverview.setFillsViewportHeight(true);
		panelOverview.add(tableOverview);

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
		lblZipcode.setBounds(40, 275, 57, 14);
		panelCheckIn.add(lblZipcode);

		JLabel lblBirthday = new JLabel("Birthday:");
		lblBirthday.setBounds(40, 325, 57, 14);
		panelCheckIn.add(lblBirthday);

		JLabel lblPaymethod = new JLabel("Paymethod:");
		lblPaymethod.setBounds(40, 375, 69, 14);
		panelCheckIn.add(lblPaymethod);

		textFieldFirstName = new JTextField();
		textFieldFirstName.setBounds(107, 70, 142, 25);
		panelCheckIn.add(textFieldFirstName);
		textFieldFirstName.setColumns(10);
		textFieldFirstName.setName("textfield First Name");

		textFieldLastName = new JTextField();
		textFieldLastName.setBounds(107, 120, 142, 25);
		panelCheckIn.add(textFieldLastName);
		textFieldLastName.setColumns(10);
		textFieldLastName.setName("textfield Last Name");

		textFieldStreet = new JTextField();
		textFieldStreet.setBounds(107, 170, 142, 25);
		panelCheckIn.add(textFieldStreet);
		textFieldStreet.setColumns(10);
		textFieldStreet.setName("textfield Street");

		textFieldCity = new JTextField();
		textFieldCity.setBounds(107, 220, 142, 25);
		panelCheckIn.add(textFieldCity);
		textFieldCity.setColumns(10);
		textFieldCity.setName("textfield City");

		textFieldZipCode = new JTextField();
		textFieldZipCode.setBounds(107, 270, 142, 25);
		panelCheckIn.add(textFieldZipCode);
		textFieldZipCode.setColumns(10);
		textFieldZipCode.setName("textfield Zip code");

		textFieldBirthday = new JTextField();
		textFieldBirthday.setBounds(107, 320, 142, 25);
		panelCheckIn.add(textFieldBirthday);
		textFieldBirthday.setColumns(10);
		textFieldBirthday.setText("e.g. 05.24.1992");
		textFieldBirthday.setName("textfield Birthday");
		textFieldBirthday.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (textFieldBirthday.getText().equals("")) {
					textFieldBirthday.setText("e.g. 05.24.1992");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (textFieldBirthday.getText().equals("e.g. 05.24.1992")) {
					textFieldBirthday.setText("");
				}
			}
		});

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Credit Card", "Debit Card" }));
		comboBox.setBounds(107, 370, 142, 25);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelPayMethod.getLayout();
				if (comboBox.getSelectedIndex() == 0) {
					c1.show(panelPayMethod, "CreditCard");
				} else {
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
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment paymentTyp = null;
				switch (comboBox.getSelectedItem().toString()) {
				case "Debit Card":
					paymentTyp = new DebitCard(textFieldNameOnCard.getText(),
							textFieldAccountNumber.getText(),
							textFieldBankNumber.getText(), textFieldNameOfBank
									.getText());
					break;
				case "Credit Card":
					paymentTyp = new CreditCard(textFieldCardHoldersName
							.getText(), textFieldCreditCardNumber.getText(),
							textFieldCVVCode.getText(), textFieldExpieringDate
									.getText());
					break;
				default:
					break;
				}
				// Payment payment = new Payment();
				DefaultMutableTreeNode treeS = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				try {
					Unit userObject = (Unit) treeS.getUserObject();
					bookRoom(paymentTyp, userObject,
							textFieldFirstName.getText(),
							textFieldLastName.getText(),
							textFieldBirthday.getText(),
							textFieldStreet.getText(), textFieldCity.getText(),
							textFieldZipCode.getText(), 1);
				} catch (Exception e2) {
					// TODO: handle exception
					System.out.println("chose room");
				}

			}
		});
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
		lblCardHoldersName.setBounds(10, 50, 115, 14);
		panelCreditCard.add(lblCardHoldersName);

		JLabel lblCreditCardNumber = new JLabel("Credit Card Number:");
		lblCreditCardNumber.setBounds(10, 100, 115, 14);
		panelCreditCard.add(lblCreditCardNumber);

		JLabel lblCvvCode = new JLabel("CVV Code:");
		lblCvvCode.setBounds(10, 150, 104, 14);
		panelCreditCard.add(lblCvvCode);

		JLabel lblExperingDate = new JLabel("Expering Date:");
		lblExperingDate.setBounds(10, 200, 104, 14);
		panelCreditCard.add(lblExperingDate);

		textFieldCardHoldersName = new JTextField();
		textFieldCardHoldersName.setBounds(135, 45, 150, 25);
		panelCreditCard.add(textFieldCardHoldersName);
		textFieldCardHoldersName.setColumns(10);

		textFieldCreditCardNumber = new JTextField();
		textFieldCreditCardNumber.setBounds(135, 95, 150, 25);
		panelCreditCard.add(textFieldCreditCardNumber);
		textFieldCreditCardNumber.setColumns(10);

		textFieldCVVCode = new JTextField();
		textFieldCVVCode.setBounds(135, 145, 150, 25);
		panelCreditCard.add(textFieldCVVCode);
		textFieldCVVCode.setColumns(10);

		textFieldExpieringDate = new JTextField();
		textFieldExpieringDate.setBounds(135, 195, 150, 25);
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

		JLabel lblNameOfBank = new JLabel("Name of Bank:");
		lblNameOfBank.setBounds(10, 200, 104, 14);
		panelDebitCard.add(lblNameOfBank);

		textFieldNameOfBank = new JTextField();
		textFieldNameOfBank.setColumns(10);
		textFieldNameOfBank.setBounds(124, 194, 150, 25);
		panelDebitCard.add(textFieldNameOfBank);

		JPanel panelCheckOut = new JPanel();
		panelCheckOut.setBackground(SystemColor.inactiveCaption);
		panelCheckOut.setForeground(SystemColor.inactiveCaption);
		panelCards.add(panelCheckOut, "CheckOut");
		panelCheckOut.setLayout(null);

		tableCheckOutModel = new DefaultTableModel();
		tableCheckOutModel.setColumnIdentifiers(columNames);
		tableCheckOut = new JTable(tableCheckOutModel) {
			/**
			 * Überschreibt die isCellEditable Methode aus der JTable Definition
			 * und sorgt so dafür, dass die Zellen nicht editierbar sind.
			 */
			public boolean isCellEditable(int x, int y) {
				return false;
			}
		};
		tableCheckOut.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableCheckOut.setForeground(SystemColor.desktop);
		tableCheckOut.setFillsViewportHeight(true);
		tableCheckOut.setColumnSelectionAllowed(true);
		tableCheckOut.setCellSelectionEnabled(true);
		tableCheckOut.getTableHeader().setReorderingAllowed(false);
		tableCheckOut.getTableHeader().setResizingAllowed(false);
		tableCheckOut.setAutoCreateColumnsFromModel(false);
		tableCheckOut.setBackground(SystemColor.inactiveCaption);
		// tableCheckOut.setFillsViewportHeight(true);
		tableCheckOut.setFont(new Font("Serif", Font.PLAIN, 14));
		tableCheckOut.getTableHeader().setFont(
				new Font("Serif", Font.PLAIN, 15));

		JScrollPane scrollPane = new JScrollPane(tableCheckOut);
		scrollPane.setBounds(10, 11, 705, 452);
		panelCheckOut.add(scrollPane);

		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g_handler.commitCheckOut();
				Receipt r = new Receipt();
				try {
					r.writePdf(model
							.getBookingFromRoom((Unit) getAusgewaehlterKnoten()
									.getUserObject()));
					r.createPDF();
					model.addReceipts(r);
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCheckOut.setBounds(626, 474, 89, 29);
		panelCheckOut.add(btnCheckOut);

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

	protected void resetTextfields() {
		textFieldFirstName.setText(null);
		textFieldLastName.setText(null);
		textFieldStreet.setText(null);
		textFieldCity.setText(null);
		textFieldZipCode.setText(null);
		textFieldBirthday.setText(null);
		textFieldCardHoldersName.setText(null);
		textFieldCreditCardNumber.setText(null);
		textFieldCVVCode.setText(null);
		textFieldExpieringDate.setText(null);
		textFieldNameOnCard.setText(null);
		textFieldAccountNumber.setText(null);
		textFieldBankNumber.setText(null);

	}

	protected void getBuchungen(DefaultMutableTreeNode data,
			ArrayList<Object[]> list) {
		if (!data.isLeaf()) {
			for (int i = 0; i < data.getChildCount(); i++) {
				getBuchungen((DefaultMutableTreeNode) data.getChildAt(i), list);

			}
		} else {
			Unit unit = (Unit) data.getUserObject();
			if (unit.isOccupied() == true) {
				Booking booking = unit.getBooking().get(
						unit.getBooking().size() - 1);
				list.add(booking.returnObject());
			}
		}

	}

	public void bookRoom(Payment paymentTyp, Unit userObject, String firstName,
			String lastName, String birthday, String street, String city,
			String zipCode, int numberOfPersons) {
		if (userObject.isHasChild() || userObject.isOccupied()) {
			JOptionPane
					.showMessageDialog(
							null,
							"You can't book this room. This room is already occupied or not a bookable unit!",
							"Invalid room", JOptionPane.ERROR_MESSAGE);
			System.out.println("Fehler");
		} else {
			try {
				Booking newBooking = new Booking();
				newBooking.setFirstNameOfBooker(textFieldFirstName.getText());
				newBooking.setLastNameOfBooker(textFieldLastName.getText());
				// newBooking.setBirthday(textFieldBirthday.getText());
				newBooking.setStreet(textFieldStreet.getText());
				newBooking.setCity(textFieldCity.getText());
				newBooking.setZipCode(textFieldZipCode.getText());
				newBooking.setNumberOfPersons(1);
				newBooking.setRoom(userObject);
				newBooking.setCheckInDate(new Date(System.currentTimeMillis()));
				newBooking.setPaymentType(paymentTyp);
				userObject.setOccupied(true);

				Date date = new SimpleDateFormat("MM.dd.yyyy")
						.parse(textFieldBirthday.getText());
				newBooking.setBirthday(date);
				model.addBookingToRoom(newBooking, userObject);
				model.addBookingToList(newBooking);
				userObject.addBooking(newBooking);
				resetTextfields();
			} catch (Exception e) {
				System.out.println("birthday incorrect");
			}

		}
	}

	protected String checkText(JTextField textField) {
		if (textField.getText().equals("")) {
			// show Popup
			System.out.println("Mistake in " + textField.getName());
		}
		return textField.getText();
	}

	public DefaultMutableTreeNode getAusgewaehlterKnoten() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
}
