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

	private String[] columNamesBooking = { "Building", "Apartment", "Room",
			"Lastname", "Firstname", "Check-In", "Paymenttype", "Birthday" };
	private String[] columNamesLastBooking = { "Room", "Lastname", "Firstname",
			"Check-In", "Check-Out", "Cost" };
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
					exData.loadSampleBookings(25, my_handler);

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

	/**
	 * Initialize the contents of the frame.
	 */
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

		frmHousingManagementSystem.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("images/Home.png"));
		mainPanel = new JPanel();
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				Color.LIGHT_GRAY, null));
		frmHousingManagementSystem.getContentPane().add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));
		try {
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		tree = new JTree(model.getRoot()); // model.getRoot()
		mainPanel.add(tree, BorderLayout.WEST);
		tree.addTreeSelectionListener(new Eventlistener() {
			public void valueChanged(TreeSelectionEvent treeEvent) {
				DefaultMutableTreeNode treeNode;
				treeNode = getAusgewaehlterKnoten();
				if (null != treeNode) {
					switch (cardNumber) {
					case 1:
						if (treeNode.isLeaf()) {
							tableHomeModel.setDataVector(null,
									columNamesLastBooking);
							tableHome.updateUI();
							Unit unit = (Unit) treeNode.getUserObject();
							if(!unit.getFinishedBookings().isEmpty()){
								Object[][] data = new Object[unit.getBooking().size()-1][];
								for (int i = 0; i < unit.getBooking().size()-1; i++) {
									data[i] = unit.getBooking().get(i).returnObjectForHome();
								}
								tableHomeModel.setDataVector(data,
										columNamesLastBooking);
								tableHome.updateUI();
							}
						}
						break;
						
					case 3:
						ArrayList<Object[]> list = new ArrayList<>();
						getBuchungen(treeNode, list);

						Object[][] data = new Object[list.size()][];
						for (int i = 0; i < list.size(); i++) {
							data[i] = list.get(i);
						}
						tableCheckOutModel.setDataVector(data,
								columNamesBooking);
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

		createMenubar();
	}

	private void createHomePanel() {
		panelCards = new JPanel();
		mainPanel.add(panelCards, BorderLayout.CENTER);
		panelCards.setLayout(new CardLayout(0, 0));

		JPanel panelHome = new JPanel();
		panelHome.setBackground(SystemColor.inactiveCaption);
		panelCards.add(panelHome, "Home");
		panelHome.setLayout(null);

		tableHomeModel = new DefaultTableModel();
		tableHomeModel.setColumnIdentifiers(columNamesLastBooking);
		tableHome = new JTable(tableHomeModel);
		tableHome.setBackground(SystemColor.activeCaption);
		JScrollPane pane = new JScrollPane(tableHome);
		pane.setBounds(20, 57, 608, 406);
		panelHome.add(pane);

		JLabel lblLastBookingsFor = new JLabel("Last Bookings for this Room:");
		lblLastBookingsFor.setBounds(25, 32, 189, 14);
		panelHome.add(lblLastBookingsFor);
	}

	private void createButtonPanel() {
		JPanel panelButtons = new JPanel();
		mainPanel.add(panelButtons, BorderLayout.SOUTH);
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));

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
		panelButtons.add(btnHome);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panelButtons.add(horizontalStrut);

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
		panelButtons.add(btnCheckIn);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panelButtons.add(horizontalStrut_1);

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
		panelButtons.add(btnCheckout);

		JButton btnOverview = new JButton("Search");
		btnOverview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout c1 = (CardLayout) panelCards.getLayout();
				c1.show(panelCards, "Overview");
				cardNumber = 4;
			}
		});

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panelButtons.add(horizontalStrut_2);
		panelButtons.add(btnOverview);
	}

	@SuppressWarnings("serial")
	private void createSearchPanel() {
		JPanel panelSearch = new JPanel();
		panelSearch.setBackground(SystemColor.inactiveCaption);
		panelCards.add(panelSearch, "Overview");
		panelSearch.setLayout(null);

		textFieldSearch = new JTextField();
		textFieldSearch.setBounds(148, 49, 122, 28);
		panelSearch.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		textFieldSearch.addKeyListener(new Eventlistener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((e.getKeyChar() >= 60 && e.getKeyChar() <= 90)
						|| (e.getKeyChar() >= 97 && e.getKeyChar() <= 122)) {
					textFieldSearch.setText(textFieldSearch.getText()
							+ e.getKeyChar());
					List<Booking> names = g_handler.searchName(textFieldSearch
							.getText());

					Object[][] data = new Object[names.size()][];
					for (int i = 0; i < names.size(); i++) {
						data[i] = names.get(i).returnObjectForCheckIn();
					}
					tableSearchModel.setDataVector(data, columNamesBooking);
					tableSearch.updateUI();
					textFieldSearch.setText(textFieldSearch.getText()
							.substring(0,
									textFieldSearch.getText().length() - 1));
				}
				if (e.getKeyChar() == 8) {
					textFieldSearch.setText(textFieldSearch.getText());
					List<Booking> names = g_handler.searchName(textFieldSearch
							.getText());
					Object[][] data = new Object[names.size()][];
					for (int i = 0; i < names.size(); i++) {
						data[i] = names.get(i).returnObjectForCheckIn();
					}
					tableSearchModel.setDataVector(data, columNamesBooking);
					tableSearch.updateUI();
				}
			}
		});

		tableSearchModel = new DefaultTableModel();
		tableSearchModel.setColumnIdentifiers(columNamesBooking);
		tableSearch = new JTable(tableSearchModel) {
			/**
			 * Überschreibt die isCellEditable Methode aus der JTable Definition
			 * und sorgt so dafür, dass die Zellen nicht editierbar sind.
			 */
			public boolean isCellEditable(int x, int y) {
				return false;
			}
		};
		tableSearch.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableSearch.setForeground(SystemColor.desktop);
		tableSearch.setFillsViewportHeight(true);
		tableSearch.setColumnSelectionAllowed(true);
		tableSearch.setCellSelectionEnabled(true);
		tableSearch.getTableHeader().setReorderingAllowed(false);
		tableSearch.getTableHeader().setResizingAllowed(false);
		tableSearch.setAutoCreateColumnsFromModel(false);
		tableSearch.setBackground(SystemColor.activeCaption);
		tableSearch.setFillsViewportHeight(true);
		tableSearch.setFont(new Font("Serif", Font.PLAIN, 14));
		tableSearch.getTableHeader().setFont(new Font("Serif", Font.PLAIN, 15));

		JScrollPane pane = new JScrollPane(tableSearch);
		pane.setBounds(30, 88, 702, 355);
		panelSearch.add(pane);

		JLabel lblSearch = new JLabel("Enter Lastname:");
		lblSearch.setBounds(30, 56, 108, 14);
		panelSearch.add(lblSearch);
	}

	private void createCheckinPanel() {
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
					JOptionPane.showMessageDialog(null,
							"Please choose a room in the tree first", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnSave.setBounds(586, 480, 89, 23);
		panelCheckIn.add(btnSave);

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
	}

	@SuppressWarnings("serial")
	private void createCheckoutPanel() {
		panelCheckOut = new JPanel();
		panelCheckOut.setBackground(SystemColor.inactiveCaption);
		panelCheckOut.setForeground(SystemColor.inactiveCaption);
		panelCards.add(panelCheckOut, "CheckOut");
		panelCheckOut.setLayout(null);

		tableCheckOutModel = new DefaultTableModel();
		tableCheckOutModel.setColumnIdentifiers(columNamesBooking);
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
		tableCheckOut.setBackground(SystemColor.activeCaption);
		// tableCheckOut.setFillsViewportHeight(true);
		tableCheckOut.setFont(new Font("Serif", Font.PLAIN, 14));
		tableCheckOut.getTableHeader().setFont(
				new Font("Serif", Font.PLAIN, 15));

		JScrollPane scrollPane = new JScrollPane(tableCheckOut);
		scrollPane.setBounds(10, 11, 718, 452);
		panelCheckOut.add(scrollPane);

		btnCheckOut = new JButton("Check Out");
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
		btnCheckOut.setBounds(639, 474, 89, 29);
		panelCheckOut.add(btnCheckOut);
	}

	private void createMenubar() {
		menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		frmHousingManagementSystem.setJMenuBar(menuBar);

		menuFile = new JMenu("File");
		menuFile.setIcon(null);
		menuFile.setForeground(Color.BLACK);
		menuFile.setBackground(Color.WHITE);
		menuBar.add(menuFile);

		menuItemClose = new JMenuItem("Close");
		menuItemClose.setIcon(new ImageIcon("images/Close-icon.png"));
		menuItemClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(menuItemClose);

		menuHelp = new JMenu("Help");
		menuHelp.setForeground(Color.BLACK);
		menuBar.add(menuHelp);

		menuItemManual = new JMenuItem("Manual");
		menuItemManual.setIcon(new ImageIcon("images/Manual.png"));
		menuHelp.add(menuItemManual);

		menuItemAbout = new JMenuItem("About");
		menuItemAbout.setIcon(new ImageIcon("images/about-us.png"));
		menuHelp.add(menuItemAbout);
	}

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
	}

	private void getBuchungen(DefaultMutableTreeNode data,
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
				list.add(booking.returnObjectForCheckIn());
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
		} else {
			try {
				Date date = new SimpleDateFormat("MM.dd.yyyy")
						.parse(textFieldBirthday.getText());
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
				newBooking.setBirthday(date);
				model.addBookingToRoom(newBooking, userObject);
				model.addBookingToList(newBooking);
				userObject.addBooking(newBooking);
				resetTextfields();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Format of the birthday is not correct", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private String checkText(JTextField textField) {
		if (textField.getText().equals("")) {
			// show Popup
			System.out.println("Mistake in " + textField.getName());
		}
		return textField.getText();
	}

	public void announceHandler(ActionListener handler, GUI_handler g_handler) {
		this.g_handler = g_handler;
	}

	public void announceModel(Model model) {
		this.model = model;
	}

	public DefaultMutableTreeNode getAusgewaehlterKnoten() {
		return (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
	}
}
