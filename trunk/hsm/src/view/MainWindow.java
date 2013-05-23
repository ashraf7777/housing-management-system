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
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TabExpander;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import model.Booking;
import model.CreditCard;
import model.DebitCard;
import model.ExampleData;
import model.Model;
import model.Payment;
import model.Receipt;
import model.Unit;

import com.itextpdf.text.DocumentException;

import controller.Eventlistener;
import controller.GUI_handler;

/**
 * 
 * This class create the GUI and handles the action on buttons, on the tree and
 * in tables.
 * 
 */
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
	private JTree tree;
	private int cardNumber;
	private Model model;
	private GUI_handler g_handler; // muss wieder gelöscht werden!!!!!!
	private JTextField textFieldNameOfBank;
	private JTable tableCheckOut;
	private DefaultTableModel tableCheckOutModel;

	private String[] columnNamesBooking = { "Building", "Apartment", "Room",
			"Lastname", "Firstname", "Check-In", "Paymenttype", "Birthday" };
	private String[] columnNamesLastBooking = { "Room", "Lastname",
			"Firstname", "Check-In", "Check-Out", "Cost" };
	private String[] columnNamesInvoices = { "Booking ID", "Lastname",
			"Firstname", "Checkin", "CheckOut", "Cost" };

	private JTextField textFieldSearch;
	private JTable tableSearch;
	private DefaultTableModel tableSearchModel;
	private JMenu menuHelp;
	private JMenuItem menuItemManual;
	private JMenuItem menuItemAbout;
	private JMenuItem menuItemClose;
	private JMenu menuFile;
	private JMenuBar menuBar;
	private JButton btnCheckOut;
	private JPanel panelCheckOut;
	private JPanel mainPanel;
	private JTable tableHome;
	private DefaultTableModel tableHomeModel;
	private JTable tableInvoices;
	private DefaultTableModel tableInvoicesModel;

	/**
	 * Create the application.
	 */
	public MainWindow(Model model) {
		this.model = model;
		initialize();
		this.frmHousingManagementSystem.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// create the Window and chose the title, bounds, closing
		// operation and icon
		frmHousingManagementSystem = new JFrame();
		frmHousingManagementSystem
				.setFont(new Font("Agency FB", Font.PLAIN, 12));
		frmHousingManagementSystem.setTitle("Housing Management System");
		frmHousingManagementSystem.setBounds(100, 100, 900, 600);
		frmHousingManagementSystem
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmHousingManagementSystem.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("images/Home.png"));
		// create the main panel for the application and add it to the frame
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				Color.LIGHT_GRAY, null));
		frmHousingManagementSystem.getContentPane().add(mainPanel);

		// set the look and feel for our application to give the aplication a
		// cool look we chose the niumbus look and feel
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		// creates the panel for the card. cardlayout means that we can switch
		// between card on show different views on the same position
		panelCards = new JPanel();
		mainPanel.add(panelCards, BorderLayout.CENTER);
		panelCards.setLayout(new CardLayout(0, 0));

		// create the tree which shows the structure of the property and add the
		// tree to the main panel
		tree = new JTree(model.getRoot());
		mainPanel.add(tree, BorderLayout.WEST);
		// add an actionlistner to the tree which fires an action if you select
		// a node
		tree.addTreeSelectionListener(new Eventlistener() {
			public void valueChanged(TreeSelectionEvent treeEvent) {
				// get the node
				DefaultMutableTreeNode treeNode;
				treeNode = getAusgewaehlterKnoten();
				// if something is selected in the tree
				if (null != treeNode) {
					// distinguish between the checkout and home panel, because
					// the need different actions on the tree
					switch (cardNumber) {
					case 1:
						// if the tree node is a leaf it will write information
						// about all finished bookings for this leaf in to the
						// table
						if (treeNode.isLeaf()) {
							tableHomeModel.setDataVector(null,
									columnNamesLastBooking);
							tableHome.updateUI();
							Unit unit = (Unit) treeNode.getUserObject();
							// if any finished bookings are availabel for this
							// room
							if (!unit.getFinishedBookings().isEmpty()) {
								// get the data for each booking
								Object[][] data = new Object[unit
										.getFinishedBookings().size()][];
								for (int i = 0; i < unit.getFinishedBookings()
										.size(); i++) {
									data[i] = unit.getFinishedBookings().get(i)
											.returnObjectForHome();
								}
								// and write it in the table
								tableHomeModel.setDataVector(data,
										columnNamesLastBooking);
								tableHome.updateUI();
							}
						}
						break;
					// in case three the function getBuchungen returns all open
					// booking for the leaf or node and will wirte them in to
					// the table
					case 3:
						ArrayList<Object[]> list = new ArrayList<>();
						getBuchungen(treeNode, list);

						Object[][] data = new Object[list.size()][];
						for (int i = 0; i < list.size(); i++) {
							data[i] = list.get(i);
						}
						tableCheckOutModel.setDataVector(data,
								columnNamesBooking);
						tableCheckOut.updateUI();
						break;

					default:
						break;
					}
				}
			}
		});

		createHomePanel();

		createButtonPanel();

		createSearchPanel();

		createCheckinPanel();

		createCheckoutPanel();

		createInvoicesPanel();

		createMenubar();
	}

	/**
	 * create the home panel with all its components
	 */
	private void createHomePanel() {
		// create the panel for the home card
		JPanel panelHome = new JPanel();
		panelHome.setBackground(SystemColor.inactiveCaption);
		panelCards.add(panelHome, "Home");
		panelHome.setLayout(null);

		// create the tablemodel for the table which stores the data
		tableHomeModel = new DefaultTableModel();
		tableHomeModel.setColumnIdentifiers(columnNamesLastBooking);

		// create the table to show the data
		tableHome = new JTable(tableHomeModel) {
			/**
			 * overides the isCellEditable function from the JTable definition
			 * the cells are not editable anymore
			 */
			public boolean isCellEditable(int x, int y) {
				return false;
			}
		};
		tableHome.setBackground(SystemColor.activeCaption);
		JScrollPane pane = new JScrollPane(tableHome);
		pane.setBounds(20, 57, 608, 406);
		panelHome.add(pane);

		// create a label for information purpose
		JLabel lblLastBookingsFor = new JLabel("Last Bookings for this Room:");
		lblLastBookingsFor.setBounds(25, 32, 189, 14);
		panelHome.add(lblLastBookingsFor);
	}

	/**
	 * create the panel for the button which are used to switch between the
	 * different cards
	 */
	private void createButtonPanel() {
		// create the panel
		JPanel panelButtons = new JPanel();
		mainPanel.add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));

		// create the button
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the layout and switch to the card with the name home
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "Home");
				cardNumber = 1;
				// show the version of the tree for the home card
				TreeModel treeModel = new DefaultTreeModel(g_handler.showHome());
				tree.setModel(treeModel);
				// set the visibility of the tree true
				tree.setVisible(true);
			}
		});
		// add the button to the panel
		btnHome.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelButtons.add(btnHome);

		// add a gap between the buttons
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelButtons.add(horizontalStrut);

		// create the button
		JButton btnCheckIn = new JButton("Check-In");
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the layout and switch to the card with the name checkin
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "CheckIn");
				cardNumber = 2;
				// show the version of the tree for the check in card
				TreeModel treeModel = new DefaultTreeModel(g_handler
						.showCheckInTree());
				tree.setModel(treeModel);
				tree.setVisible(true);
			}
		});
		// add the button
		panelButtons.add(btnCheckIn);

		// creta a gap between the buttons
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panelButtons.add(horizontalStrut_1);

		// see above
		JButton btnCheckout = new JButton("Check-Out");
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the layout and switch to the card with the name checkout
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "CheckOut");
				cardNumber = 3;
				// show the version of the tree for the check in card
				TreeModel treeModel2 = new DefaultTreeModel(g_handler
						.showCheckOutTree());
				tree.setModel(treeModel2);
				tree.setSelectionRow(0);
				tree.setVisible(true);
			}
		});
		panelButtons.add(btnCheckout);

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the layout and switch to the card with the name search
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "Search");
				cardNumber = 4;
				// set the visiblility of the tree false
				tree.setVisible(false);
			}
		});

		// create gaps between the buttons
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panelButtons.add(horizontalStrut_2);
		panelButtons.add(btnSearch);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panelButtons.add(horizontalStrut_3);

		JButton btnInvoice = new JButton("Invoices");
		panelButtons.add(btnInvoice);
		btnInvoice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// get the layout and show the card with the name invoices
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "Invoices");
				cardNumber = 5;

				// get al invoices and write them into the table
				Object[][] data = new Object[model.getAllReceipts().size()][];
				for (int i = 0; i < model.getAllReceipts().size(); i++) {
					data[i] = model.getAllReceipts().get(i).returnForInvoices();
				}
				tableInvoicesModel.setDataVector(data, columnNamesInvoices);
				tableInvoices.updateUI();
				tree.setVisible(false);

			}
		});
	}

	/**
	 * create the search panel
	 */
	@SuppressWarnings("serial")
	private void createSearchPanel() {
		// create the panel
		JPanel panelSearch = new JPanel();
		panelSearch.setBackground(SystemColor.inactiveCaption);
		panelCards.add(panelSearch, "Search");
		panelSearch.setLayout(null);

		// create the search textfield
		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(197, 49, 122, 28);
		panelSearch.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		// add a keylistener for the textfield
		textFieldSearch.addKeyListener(new Eventlistener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// if the press key is an letter from to z or A to Z..
				if ((e.getKeyChar() >= 60 && e.getKeyChar() <= 90)
						|| (e.getKeyChar() >= 97 && e.getKeyChar() <= 122)) {
					// Use the method searchName to find a name which starts
					// with the letters in the textfield
					textFieldSearch.setText(textFieldSearch.getText()
							+ e.getKeyChar());
					List<Booking> names = g_handler.searchName(textFieldSearch
							.getText());
					// enter the data of all bookings and names that start with
					// the prefix from the textfield in the table
					Object[][] data = new Object[names.size()][];
					for (int i = 0; i < names.size(); i++) {
						data[i] = names.get(i).returnObjectForCheckIn();
					}
					// set the data in the table
					tableSearchModel.setDataVector(data, columnNamesBooking);
					tableSearch.updateUI();
					textFieldSearch.setText(textFieldSearch.getText()
							.substring(0,
									textFieldSearch.getText().length() - 1));
				}
				// if the press key is backspace..
				if (e.getKeyChar() == 8) {
					// delete the last letter in the tetfield and refresh the
					// table
					textFieldSearch.setText(textFieldSearch.getText());
					List<Booking> names = g_handler.searchName(textFieldSearch
							.getText());
					Object[][] data = new Object[names.size()][];
					for (int i = 0; i < names.size(); i++) {
						data[i] = names.get(i).returnObjectForCheckIn();
					}
					tableSearchModel.setDataVector(data, columnNamesBooking);
					tableSearch.updateUI();
				}
			}
		});

		// create the tablemodel for the search table that stores the data
		tableSearchModel = new DefaultTableModel();
		tableSearchModel.setColumnIdentifiers(columnNamesBooking);

		// create table for search table
		tableSearch = new JTable(tableSearchModel) {
			/**
			 * Überschreibt die isCellEditable Methode aus der JTable Definition
			 * und sorgt so dafür, dass die Zellen nicht editierbar sind.
			 */
			public boolean isCellEditable(int x, int y) {
				return false;
			}
		};
		// set border
		tableSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		// set the color of the foreground
		tableSearch.setForeground(SystemColor.desktop);
		tableSearch.setFillsViewportHeight(true);
		tableSearch.setColumnSelectionAllowed(true);
		tableSearch.setCellSelectionEnabled(true);
		// disables the reordering and resizing of the table header
		tableSearch.getTableHeader().setReorderingAllowed(false);
		tableSearch.getTableHeader().setResizingAllowed(false);
		// set background color
		tableSearch.setBackground(SystemColor.activeCaption);
		tableSearch.setFillsViewportHeight(true);
		// set font of table and header
		tableSearch.setFont(new Font("Serif", Font.PLAIN, 14));
		tableSearch.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 15));

		// the table to a scrollpane so that the table has a scrollbar
		JScrollPane pane = new JScrollPane(tableSearch);
		pane.setBounds(88, 88, 702, 355);
		panelSearch.add(pane);

		// create label for information purpose
		JLabel lblSearch = new JLabel("Enter Lastname:");
		lblSearch.setBounds(88, 56, 108, 14);
		panelSearch.add(lblSearch);
	}

	/**
	 * create the checkin panel
	 */
	private void createCheckinPanel() {
		// in this method a lot of components are create is always the same
		// procedure so they are not all commented just once

		// create panel and set color
		JPanel panelCheckIn = new JPanel();
		panelCards.add(panelCheckIn, "CheckIn");
		panelCheckIn.setBackground(SystemColor.inactiveCaption);
		panelCheckIn.setLayout(null);

		// create a label for information purpose
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

		// create a textfield for entering input data
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
		// if you click in the textfield the example text disappears. If you
		// leave the textfield without writing anything the example texte
		// appears again
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

		// create a combobox to chose the payment type
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Credit Card", "Debit Card" }));
		comboBox.setBounds(107, 370, 142, 25);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// show the inputcard for credit crad or debit card payment
				CardLayout c1 = (CardLayout) panelPayMethod.getLayout();
				if (comboBox.getSelectedIndex() == 0) {
					c1.show(panelPayMethod, "CreditCard");
				} else {
					c1.show(panelPayMethod, "DebitCard");
				}
			}
		});
		panelCheckIn.add(comboBox);

		// create a separator for design prupose
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

		// create the button to save the check in
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment paymentTyp = null;
				// distinguish between the choosen payment type and ready the
				// data and safe it to the object
				switch (comboBox.getSelectedItem().toString()) {
				case "Debit Card":
					if(textFieldNameOnCard.getText().equals(""))
					{
						showErrorMsg(textFieldNameOnCard, "Name on Card");
						return;
					}
					if (!isStringANumber(textFieldAccountNumber.getText())) {
						showErrorMsg(textFieldAccountNumber, "Account Number");
						return;
					}
					if (!isStringANumber(textFieldBankNumber.getText())) {
						showErrorMsg(textFieldBankNumber, "Bank Number");
						return;
					}
					if(textFieldNameOfBank.getText().equals(""))
					{
						showErrorMsg(textFieldNameOfBank, "Name of Bank");
						return;
					}
					paymentTyp = new DebitCard(textFieldNameOnCard.getText(),
							textFieldAccountNumber.getText(),
							textFieldBankNumber.getText(), textFieldNameOfBank
									.getText());
					paymentTyp.setName("Debit Card"); 
					break;
				case "Credit Card":
					if(textFieldCardHoldersName.getText().equals(""))
					{
						showErrorMsg(textFieldCardHoldersName, "Card Holders Name");
						return;
					}
					if (!isStringANumber(textFieldCreditCardNumber.getText())) {
						showErrorMsg(textFieldCreditCardNumber,
								"CreditCard Number");
						return;
					}
					if (!isStringANumber(textFieldCVVCode.getText())) {
						showErrorMsg(textFieldCVVCode, "CVV Number");
						return;
					}
					if(textFieldExpieringDate.getText().equals(""))
					{
						showErrorMsg(textFieldExpieringDate, "Expiring Date");
						return;
					}
					paymentTyp = new CreditCard(textFieldCardHoldersName
							.getText(), textFieldCreditCardNumber.getText(),
							textFieldCVVCode.getText(), textFieldExpieringDate
									.getText());
					paymentTyp.setName("Credit Card");
					break;
				default:
					break;
				}
				// get the selected unit in the tree
				DefaultMutableTreeNode treeS = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				try {
					// get the unit object from the tree to safe the booking to
					// the unit
					Unit userObject = (Unit) treeS.getUserObject();
					// book the room
					if (bookRoom(paymentTyp, userObject)) {
						// refresh the tree
						TreeModel treeModel = new DefaultTreeModel(g_handler
								.showCheckInTree());
						tree.setModel(treeModel);
					}
				} catch (Exception e2) {
					// show error message if an mistake occurs
					JOptionPane.showMessageDialog(null,
							"Please choose a room in the tree first", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				//FIXME: nach dem speichern werden alle Felder gelöscht bis auf name of bank bei der debit card
			}
		});
		btnSave.setBounds(586, 480, 89, 23);
		panelCheckIn.add(btnSave);

		// create the panel with the cardlayout for the pyment type
		panelPayMethod = new JPanel();
		panelPayMethod.setBackground(SystemColor.activeCaption);
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

		JLabel lblExpiringDate = new JLabel("Expiring Date:");
		lblExpiringDate.setBounds(10, 200, 104, 14);
		panelCreditCard.add(lblExpiringDate);

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
	}

	/**
	 * create the checkout panel
	 */
	@SuppressWarnings("serial")
	private void createCheckoutPanel() {
		// create the panel
		panelCheckOut = new JPanel();
		panelCheckOut.setForeground(SystemColor.inactiveCaption);
		panelCards.add(panelCheckOut, "CheckOut");
		panelCheckOut.setLayout(null);

		// create the table for the checkout card
		tableCheckOutModel = new DefaultTableModel();
		tableCheckOutModel.setColumnIdentifiers(columnNamesBooking);
		tableCheckOut = new JTable(tableCheckOutModel) {
			/**
			 * Überschreibt die isCellEditable Methode aus der JTable Definition
			 * und sorgt so dafür, dass die Zellen nicht editierbar sind.
			 */
			public boolean isCellEditable(int x, int y) {
				return false;
			}
		};
		// do the the same settings for the table as mentioned above
		tableCheckOut.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableCheckOut.setForeground(SystemColor.desktop);
		tableCheckOut.setFillsViewportHeight(true);
		tableCheckOut.setColumnSelectionAllowed(true);
		tableCheckOut.setCellSelectionEnabled(true);
		tableCheckOut.getTableHeader().setReorderingAllowed(false);
		tableCheckOut.getTableHeader().setResizingAllowed(false);
		tableCheckOut.setBackground(SystemColor.activeCaption);
		tableCheckOut.setFont(new Font("Serif", Font.PLAIN, 14));
		tableCheckOut.getTableHeader().setFont(
				new Font("Serif", Font.PLAIN, 15));

		// create a scrollpane and add the table to it, so that the table has a
		// scrollbar
		JScrollPane scrollPane = new JScrollPane(tableCheckOut);
		scrollPane.setBounds(20, 11, 718, 452);
		panelCheckOut.add(scrollPane);

		// create the button to checkout the person
		btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
				if (g_handler.commitCheckOut()) {
					Receipt r = new Receipt();
					try {
						r.writePdf(model
								.getBookingFromRoom((Unit) getAusgewaehlterKnoten()
										.getUserObject()));
						r.createPDF();
						model.addReceipts(r);
						// refresh the tree and clear table
						tableCheckOutModel.setDataVector(null,
								columnNamesBooking);
						TreeModel treeModel = new DefaultTreeModel(g_handler
								.showCheckOutTree());
						tree.setModel(treeModel);
					} catch (DocumentException e1) {
						// TODO: Pop Ups entfernen
						JOptionPane.showMessageDialog(null, "Document");

					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "IO");
					}
				}
			}
		});
		btnCheckOut.setBounds(639, 474, 89, 29);
		panelCheckOut.add(btnCheckOut);

	}

	/**
	 * create the invoice panel
	 */
	@SuppressWarnings("serial")
	private void createInvoicesPanel() {
		// create the panel
		JPanel panelInvoices = new JPanel();
		panelInvoices.setBackground(SystemColor.inactiveCaption);
		panelCards.add(panelInvoices, "Invoices");
		panelInvoices.setLayout(null);

		// create a scrollpane for the table
		JScrollPane pane = new JScrollPane();
		pane.setBounds(88, 88, 702, 355);
		panelInvoices.add(pane);

		tableInvoicesModel = new DefaultTableModel();
		tableInvoicesModel.setColumnIdentifiers(columnNamesInvoices);

		tableInvoices = new JTable(tableInvoicesModel) {
			/**
			 * Überschreibt die isCellEditable Methode aus der JTable Definition
			 * und sorgt so dafür, dass die Zellen nicht editierbar sind.
			 */
			public boolean isCellEditable(int x, int y) {
				return false;
			}
		};
		tableInvoices.setBackground(SystemColor.activeCaption);
		// do the the same settings for the table as mentioned above
		tableInvoices.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableInvoices.setForeground(SystemColor.desktop);
		tableInvoices.setFillsViewportHeight(true);
		tableInvoices.setColumnSelectionAllowed(true);
		tableInvoices.setCellSelectionEnabled(true);
		tableInvoices.getTableHeader().setReorderingAllowed(false);
		tableInvoices.getTableHeader().setResizingAllowed(false);
		tableInvoices.setBackground(SystemColor.activeCaption);
		tableInvoices.setFont(new Font("Serif", Font.PLAIN, 14));
		tableInvoices.getTableHeader().setFont(
				new Font("Serif", Font.PLAIN, 15));
		pane.setViewportView(tableInvoices);

		// create label for information purposes
		JLabel lblInvoices = new JLabel("Invoices:");
		lblInvoices.setBounds(88, 63, 89, 14);
		panelInvoices.add(lblInvoices);

		// create the button to print the invoice
		JButton btnPrint = new JButton("Print");
		btnPrint.setBounds(701, 454, 89, 23);
		panelInvoices.add(btnPrint);

		btnPrint.addActionListener(new ActionListener() {
			// create the pdf
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					model.getAllReceipts()
							.get(tableInvoices.getSelectedRowCount() - 1)
							.createPDF();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,
							"Please choose an invoice", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (DocumentException e1) {
				}
			}
		});
	}

	/**
	 * create the menubar
	 */
	private void createMenubar() {
		// create the menubar and add it to the frame
		menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		frmHousingManagementSystem.setJMenuBar(menuBar);

		// create a menu for the menubar
		menuFile = new JMenu("File");
		menuBar.add(menuFile);

		// create a menuitem and add it to the menu
		menuItemClose = new JMenuItem("Close");
		menuItemClose.setIcon(new ImageIcon("images/Close-icon.png"));
		menuItemClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// shut down the application
				System.exit(0);
			}
		});
		menuFile.add(menuItemClose);

		menuHelp = new JMenu("Help");
		menuHelp.setForeground(Color.BLACK);
		menuBar.add(menuHelp);
		// TODO: Pop- up About us hinzufügen
		menuItemManual = new JMenuItem("Manual");
		menuItemManual.setIcon(new ImageIcon("images/Manual.png"));
		menuHelp.add(menuItemManual);
		// TODO: weitere Punkte zum Menü hinzufügen
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.setIcon(new ImageIcon("images/about-us.png"));
		menuHelp.add(menuItemAbout);
	}

	/**
	 * reset the textfields for the next checkin process
	 */
	private void resetTextfields() {
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
		textFieldNameOfBank.setText(null);
	}

	/**
	 * returns the data for the table in checkout. This is a recursiv method
	 * 
	 * @param data
	 *            selected treenode
	 * @param list
	 *            list to safe the data in
	 */
	private void getBuchungen(DefaultMutableTreeNode data,
			ArrayList<Object[]> list) {
		// if the treenode is not a leaf call getbuchen again for all children
		if (!data.isLeaf()) {
			for (int i = 0; i < data.getChildCount(); i++) {
				getBuchungen((DefaultMutableTreeNode) data.getChildAt(i), list);
			}
		} else {
			// if it is a leaf safe the data for the table in the list
			Unit unit = (Unit) data.getUserObject();
			if (unit.isOccupied() == true) {
				Booking booking = unit.getBookingList().get(
						unit.getBookingList().size() - 1);
				list.add(booking.returnObjectForCheckIn());
			}
		}
	}

	/**
	 * the method to book a room
	 * 
	 * @param paymentTyp
	 *            payment type
	 * @param userObject
	 *            the unit to add the booking on
	 * @param firstName
	 *            fistname
	 * @param lastName
	 *            lastname
	 * @param birthday
	 *            brithday
	 * @param street
	 *            street
	 * @param city
	 *            city
	 * @param zipCode
	 *            zipcode
	 * @param numberOfPersons
	 *            number of the personen who can live in this unit
	 */
	public boolean bookRoom(Payment paymentTyp, Unit userObject) {
		// If it is not a leaf or the room is already book show error
		if (userObject.isHasChild() || userObject.isOccupied()) {
			JOptionPane
					.showMessageDialog(
							null,
							"You can't book this room. This room is already occupied or not a bookable unit!",
							"Invalid room", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			// TODO: Idioten sicher machen

			if (textFieldFirstName.getText().equals("")) {
				showErrorMsg(textFieldFirstName, "First name");
				return false;
			}
			if (textFieldLastName.getText().equals("")) {
				showErrorMsg(textFieldLastName, "Last name");
				return false;
			}
			if (textFieldStreet.getText().equals("")) {
				showErrorMsg(textFieldStreet, "Street");
				return false;
			}
			if (textFieldCity.getText().equals("")) {
				showErrorMsg(textFieldCity, "City");
				return false;
			}
			if (!isStringANumber(textFieldZipCode.getText())) {
				showErrorMsg(textFieldZipCode, "ZIP Code");
				return false;
			}
			try {
				// set all the fields for the new booking
				Date date = new SimpleDateFormat("MM.dd.yyyy")
						.parse(textFieldBirthday.getText());
				Booking newBooking = new Booking();
				newBooking.setFirstNameOfBooker(textFieldFirstName.getText());
				newBooking.setLastNameOfBooker(textFieldLastName.getText());
				newBooking.setStreet(textFieldStreet.getText());
				newBooking.setCity(textFieldCity.getText());
				newBooking.setZipCode(textFieldZipCode.getText());
				newBooking.setNumberOfPersons(1);
				newBooking.setRoom(userObject);
				newBooking.setCheckInDate(new Date(System.currentTimeMillis()));
				newBooking.setPaymentType(paymentTyp);
				// set room occupied
				userObject.setOccupied(true);
				newBooking.setBirthday(date);
				// add the booking to the room, to the overall booking list and
				// to the room
				model.addBookingToRoom(newBooking, userObject);
				model.addBookingToList(newBooking);
				userObject.addBooking(newBooking);
				// reset the textfields
				resetTextfields();
				return true;
			} catch (Exception e) {
				showErrorMsg(textFieldBirthday, "Birthday");
				return false;
			}
		}
	}

	/**
	 * Checks if the provided string is a number
	 * 
	 * @param s
	 *            to be checked string
	 * @return true if the string is a number
	 */
	private boolean isStringANumber(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void showErrorMsg(JTextField textField, String sourceOfError) {
		JOptionPane.showMessageDialog(null,
				"Please control your input in the field " + sourceOfError,
				"Invalid input", JOptionPane.ERROR_MESSAGE);
		textField.setText("");
	}

	/**
	 * announce the contoller to the view so that they know each other
	 * 
	 * @param g_handler
	 *            controller
	 */
	public void announceHandler(GUI_handler g_handler) {
		this.g_handler = g_handler;
	}

	/**
	 * annopunce the model to the view so that they knwo each other
	 * 
	 * @param model
	 *            model
	 */
	public void announceModel(Model model) {
		this.model = model;
	}

	/**
	 * get the selected tree node in the tree
	 * 
	 * @return treenode
	 */
	public DefaultMutableTreeNode getAusgewaehlterKnoten() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
}
