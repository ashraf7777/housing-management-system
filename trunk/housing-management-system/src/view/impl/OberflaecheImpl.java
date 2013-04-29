package view.impl;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import lagerverwaltungStart.Main;
import model.Buchung;
import model.Lager;
import model.Lieferung;
import view.Oberflaeche;
import view.Tools;

/**
 * Implementierung des Interfaces Oberflaeche. Implementiert konkret die
 * grafische Benutzeroberfl�che.
 * 
 * @version 1.1.0
 * @author Dominik Kl�ter
 * @author Philo K�nneker
 * 
 */
public class OberflaecheImpl implements Oberflaeche {

	// ### Singeltonvariable ###
	private static Oberflaeche theInstance;

	// ### ActionListener f�r die Oberfl�chen ###
	private static ActionListener listener_Lagerverwaltung;
	private static MouseListener listener_LieferungsUebersicht;

	// ### Variable, die die Oberfl�che beinhaltet ###
	private JFrame lagerverwaltung;

	// ### Variablen f�r die Oberfl�che ###
	private JPanel p_top, p_top_sub_top, p_top_sub_bottom, p_tree, p_center, p_platzhalter1, p_platzhalter2;
	private JPanel p_center_lieferungen, p_center_lieferungdetails, p_center_lagerbuchungen;
	private JPanel p_center_neue_lieferung, p_center_neue_lieferung_north, p_center_neue_lieferung_south, p_center_neue_lieferung_center;
	private JButton redo, undo, neueLieferung, lageruebersicht, neuesLager, lagerUmbenennen;
	private JLabel l_titel;
	private JTree lagerTree;
	private JTable tbl_buchungsUebersicht, tbl_lieferungsUebersicht, tbl_lagerbuchungen;
	private JTabbedPane p_center_tabbs = new JTabbedPane();

	// ### Variablen der Ansicht "Neue Lieferung" ###
	private JTextField gesamtmenge, prozentAnteil, saldo;
	private int verbleibendeMenge = 0, verbleibenderProzentanteil = 0;
	private JLabel lagerBezeichnung, restMenge;
	private JButton btn_best, btn_abbr, btn_jetztBuchen;
	private GridBagLayout gbl;
	private JRadioButton zuBuchung, abBuchung;
	private ButtonGroup buchungsArt;

	// ### Festsethendes Datumsformat ###
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss");

	// ### Zustandsvariablen, um zu �berpr�fen, welche Sicht gezeigt wird ###
	private boolean isCardUebersichtAktiv = false;
	private boolean isCardNeueLieferungAktiv = false;

	/**
	 * privater Konstruktor (Singelton)
	 * 
	 * @author Philo K�nneker
	 */
	private OberflaecheImpl() {
		buildLagerverwaltung();
		hideUndoRedo();
		showCardUebersicht();
	}

	/**
	 * Erstellt die Oberfl�che f�r das Hauptfenster der Lagerverwaltung
	 * 
	 * @author Dominik Kl�ter
	 * @author Philo K�nneker
	 */
	private void buildLagerverwaltung() {
		if (lagerverwaltung != null)
			return;
		lagerverwaltung = new JFrame("Lagerverwaltung");
		lagerverwaltung.setSize(1000, 700);
		lagerverwaltung.setMinimumSize(new Dimension(900, 500));
		lagerverwaltung.setLocation(30, 10);
		lagerverwaltung.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		lagerverwaltung.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(lagerverwaltung, "M�chten Sie die Lagerverwaltung wirklich beenden?\n"
						+ "Einstellungen werden nicht gespeichert.", "Wirklich beenden?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
						null);
				if (confirm == JOptionPane.YES_OPTION) {
					hideLagerverwaltung();
					lagerverwaltung.dispose();
					System.exit(0);
				}
			}
		});

		Container c = lagerverwaltung.getContentPane();
		c.setLayout(new BorderLayout(10, 10)); // in Klammer kann der Freiraum zwischen den einzelnen Elementen angegeben werden

		// ### Panel f�r den 'undo' und den 'redo' Button ###
		// Panel dient als Container f�r zwei Subpanels
		p_top = new JPanel();
		p_top.setLayout(new GridLayout(2, 1)); // Tabellenlayout um die beiden Panels untereinander zu stapeln
		p_top.setSize(lagerverwaltung.getWidth(), 80);
		p_top_sub_top = new JPanel();
		l_titel = new JLabel("Lagerverwaltungstool v" + Main.VERSION);
		l_titel.setFont(new Font("Helvetica", Font.BOLD, 18));
		p_top_sub_top.add(l_titel);

		p_top_sub_bottom = new JPanel();
		GridBagLayout gbl2 = new GridBagLayout();
		p_top_sub_bottom.setLayout(gbl2);

		p_platzhalter1 = new JPanel();
		p_platzhalter2 = new JPanel();

		undo = new JButton("Undo");
		redo = new JButton("Redo");
		undo.setIcon(new ImageIcon("./images/Undo-icon.png"));
		redo.setIcon(new ImageIcon("./images/Redo-icon.png"));
		neueLieferung = new JButton("Neue Lieferung");
		neuesLager = new JButton("Neues Lager");
		lagerUmbenennen = new JButton("Lager umbenennen");
		lageruebersicht = new JButton("Lieferungs-/ Lager�bersicht");

		// ### Actionlistener bekannt machen ###
		undo.addActionListener(listener_Lagerverwaltung);
		redo.addActionListener(listener_Lagerverwaltung);
		neueLieferung.addActionListener(listener_Lagerverwaltung);
		neuesLager.addActionListener(listener_Lagerverwaltung);
		lagerUmbenennen.addActionListener(listener_Lagerverwaltung);
		lageruebersicht.addActionListener(listener_Lagerverwaltung);

		// ### Platzhalter ###
		p_platzhalter1.setPreferredSize(new Dimension(5, 30));
		p_platzhalter1.setMinimumSize(new Dimension(5, 30));
		p_platzhalter2.setPreferredSize(new Dimension(150, 30));
		p_platzhalter2.setMinimumSize(new Dimension(150, 30));

		// ### Komponenten dem unteren Unterpanel hinzuf�gen ###
		Tools.addComponent(p_top_sub_bottom, gbl2, neuesLager, 0, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_top_sub_bottom, gbl2, neueLieferung, 1, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_top_sub_bottom, gbl2, lagerUmbenennen, 2, 0, 1, 0, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_top_sub_bottom, gbl2, p_platzhalter1, 3, 0, 1, 0, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_top_sub_bottom, gbl2, undo, 4, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_top_sub_bottom, gbl2, redo, 5, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_top_sub_bottom, gbl2, p_platzhalter2, 6, 0, 5, 1, 1, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_top_sub_bottom, gbl2, lageruebersicht, 11, 0, 1, 1, 0, 0, GridBagConstraints.NONE);

		// ### Dem oberen Panel die beiden Unterpanels zuweisen ###
		p_top.add(p_top_sub_top);
		p_top.add(p_top_sub_bottom);

		// ### Baum mit Scrollbar im WEST Element ###
		lagerTree = new JTree(Lager.getTree());

		p_tree = new JPanel(new GridLayout());
		p_tree.setPreferredSize(new Dimension(250, 50)); // Breite des Trees festlegen. H�he wird aufgrund des BorderLayouts ignoriert!
		JScrollPane scrollBar = new JScrollPane(lagerTree);
		lagerTree.addTreeSelectionListener((TreeSelectionListener) listener_Lagerverwaltung);
		p_tree.add(scrollBar);

		// ### Men�auswahl im CENTER ###
		p_center_tabbs = new JTabbedPane();
		p_center_tabbs.addTab("Lieferungen", p_center_lieferungen = new JPanel());
		p_center_tabbs.addTab("Lieferungdetails", p_center_lieferungdetails = new JPanel());
		p_center_tabbs.addTab("Lagerbuchungen", p_center_lagerbuchungen = new JPanel());

		gbl = new GridBagLayout();

		// ### baut das Panel f�r "Neue Lieferung" zusammen
		buildCardNeueLieferung();

		p_center = new JPanel();
		p_center.setLayout(new CardLayout());
		p_center.add(p_center_tabbs, "�bersicht");
		p_center.add(p_center_neue_lieferung, "NeueLieferung");
		c.add(p_tree, BorderLayout.WEST);
		c.add(p_top, BorderLayout.NORTH);
		c.add(p_center, BorderLayout.CENTER);
	}

	/**
	 * Baut das Panel f�r "Neue Lieferung" zusammen
	 * 
	 * @author Dominik Kl�ter
	 * @author Philo K�nneker
	 */
	private void buildCardNeueLieferung() {
		p_center_neue_lieferung = new JPanel();
		p_center_neue_lieferung_north = new JPanel();
		p_center_neue_lieferung_center = new JPanel();
		p_center_neue_lieferung_center.setLayout(gbl);
		p_center_neue_lieferung_south = new JPanel();
		p_center_neue_lieferung_south.setLayout(gbl);
		p_center_neue_lieferung_south.setPreferredSize(new Dimension(1, 100));
		Tools.addComponent(p_center_neue_lieferung_south, gbl, btn_best = new JButton("Best�tigen"), 1, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_center_neue_lieferung_south, gbl, btn_abbr = new JButton("Abbrechen"), 3, 0, 1, 1, 0, 0, GridBagConstraints.NONE);
		p_center_neue_lieferung.setLayout(new BorderLayout());
		p_center_neue_lieferung.add(p_center_neue_lieferung_north, BorderLayout.NORTH);
		p_center_neue_lieferung.add(p_center_neue_lieferung_center, BorderLayout.CENTER);
		p_center_neue_lieferung.add(p_center_neue_lieferung_south, BorderLayout.SOUTH);

		btn_best.addActionListener(listener_Lagerverwaltung);
		btn_abbr.addActionListener(listener_Lagerverwaltung);

		zuBuchung = new JRadioButton("Zubuchen", true);
		abBuchung = new JRadioButton("Abbuchen");

		buchungsArt = new ButtonGroup();
		buchungsArt.add(zuBuchung);
		buchungsArt.add(abBuchung);

		Tools.addComponent(p_center_neue_lieferung_center, gbl, zuBuchung, 0, 0, 2, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, abBuchung, 0, 1, 2, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, new JLabel("Gesamtmenge :"), 0, 3, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, gesamtmenge = new JTextField("Gesamtmenge"), 1, 3, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, restMenge = new JLabel("Verbleibende Menge: " + verbleibendeMenge), 0, 4, 3, 1, 0, 0,
				GridBagConstraints.HORIZONTAL);
		gesamtmenge.addMouseListener(listener_LieferungsUebersicht);
		gesamtmenge.setPreferredSize(new Dimension(100, 20));

		Tools.addComponent(p_center_neue_lieferung_center, gbl, lagerBezeichnung = new JLabel(), 0, 5, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, prozentAnteil = new JTextField("Prozentualer Anteil"), 1, 5, 1, 1, 0, 0,
				GridBagConstraints.HORIZONTAL);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, new JLabel(" "), 2, 5, 1, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, btn_jetztBuchen = new JButton("Jetzt buchen"), 2, 6, 1, 1, 0, 0, GridBagConstraints.NONE);
		Tools.addComponent(p_center_neue_lieferung_center, gbl, new JLabel("Es wird automatisch abgerundet!"), 0, 8, 3, 1, 0, 0, GridBagConstraints.VERTICAL);
		prozentAnteil.addMouseListener(listener_LieferungsUebersicht);
		btn_jetztBuchen.addActionListener(listener_Lagerverwaltung);
		prozentAnteil.setPreferredSize(new Dimension(120, 20));
		restMenge.setVisible(false);
		lagerBezeichnung.setVisible(false);
		prozentAnteil.setVisible(false);
		disableJetztBuchen();
	}

	/**
	 * Setzte das Panel f�r "Neue Lieferung" auf den Anfangszustand zur�ck
	 */
	private void resetCardNeueLieferung() {
		lagerBezeichnung.setVisible(false);
		prozentAnteil.setVisible(false);
		disableJetztBuchen();
		prozentAnteil.setText("prozentualer Anteil");
	}

	@Override
	public void enableAlleBuchungenBestaetigen() {
		btn_best.setEnabled(true);
	}

	@Override
	public void enableBuchungsArt() {
		zuBuchung.setEnabled(true);
		abBuchung.setEnabled(true);
	}

	@Override
	public void enableGesamtmenge() {
		gesamtmenge.setEditable(true);
	}

	@Override
	public void enableJetztBuchen() {
		btn_jetztBuchen.setEnabled(true);
	}

	@Override
	public void enableLagerUebersicht() {
		lageruebersicht.setEnabled(true);
		neueLieferung.setEnabled(true);
	}

	@Override
	public void enableLagerUmbenennen() {
		lagerUmbenennen.setEnabled(true);
	}

	@Override
	public void enableNeuesLager() {
		neuesLager.setEnabled(true);
	}

	@Override
	public void enableRedo() {
		redo.setEnabled(true);
	}

	@Override
	public void enableUndo() {
		undo.setEnabled(true);
	}

	@Override
	public void disableBuchungsArt() {
		zuBuchung.setEnabled(false);
		abBuchung.setEnabled(false);
	}

	@Override
	public void disableNeuesLager() {
		neuesLager.setEnabled(false);
	}

	@Override
	public void disableNeueLieferung() {
		neueLieferung.setEnabled(false);
	}

	@Override
	public void disableJetztBuchen() {
		btn_jetztBuchen.setEnabled(false);
	}

	@Override
	public void disableGesamtmenge() {
		gesamtmenge.setEditable(false);
	}

	@Override
	public void disableAlleBuchungenBestaetigen() {
		btn_best.setEnabled(false);
	}

	@Override
	public void disableRedo() {
		redo.setEnabled(false);
	}

	@Override
	public void disableUndo() {
		undo.setEnabled(false);
	}

	@Override
	public void disableLagerUmbenennen() {
		lagerUmbenennen.setEnabled(false);
	}

	@Override
	public boolean isAbBuchung() {
		return abBuchung.isSelected();
	}

	@Override
	public boolean isCardNeueLieferungAktiv() {
		return isCardNeueLieferungAktiv;
	}

	@Override
	public boolean isCardUebersichtAktiv() {
		return isCardUebersichtAktiv;
	}

	/**
	 * Gibt eine List<Buchung> mit allen Buchungen, die auf dem Lager, welches
	 * �bergeben wurde, und dessen Unterlager get�tigt wurden.
	 * 
	 * @author Dominik Kl�ter
	 * 
	 * @return b Liste aller Buchungen des Lagers incl. der Buchungen aller
	 *         seiner Unterlager
	 */
	@Override
	public List<Buchung> getAllBuchungen(Lager l) {
		List<Buchung> b = new ArrayList<Buchung>();
		for (int j = 0; j < l.getChildCount(); j++) {
			if (l.getChildAt(j).isLeaf()) {
				b.addAll(((Lager) (l.getChildAt(j))).getBuchungen());
			} else {
				b.addAll(getAllBuchungen((Lager) l.getChildAt(j)));
			}
		}
		return b;
	}

	/**
	 * Gibt das aktuell in der Lagerstruktur augew�hlte Lager zur�ck
	 * 
	 * @return lager Das im Baum ausgew�hlte Lager
	 */
	@Override
	public Lager getAusgewaehlterKnoten() {
		return (Lager) lagerTree.getLastSelectedPathComponent();
	}

	@Override
	public String getGesamtmenge() {
		return gesamtmenge.getText();
	}

	@Override
	public String getProzentualerAnteil() {
		return prozentAnteil.getText();
	}

	@Override
	public JTable getTbl_lieferungsUebersicht() {
		return tbl_lieferungsUebersicht;
	}

	@Override
	public int getVerbleibendeMenge() {
		return verbleibendeMenge;
	}

	@Override
	public int getVerbleibenderProzentanteil() {
		return verbleibenderProzentanteil;
	}

	@Override
	public void setVerbleibendeMenge(int menge) {
		verbleibendeMenge = menge;
	}

	@Override
	public void setVerbleibenderProzentanteil(int verbleibenderProzentanteil) {
		this.verbleibenderProzentanteil = verbleibenderProzentanteil;
	}

	@Override
	public void hideLagerverwaltung() {
		lagerverwaltung.setVisible(false);
	}

	@Override
	public void hideUndoRedo() {
		undo.setVisible(false);
		redo.setVisible(false);
	}

	/**
	 * Zeigt das Panel "Neue Lieferung" an und versteckt das Panel
	 * "Lieferungs- & Buchungs�bersicht". Ebenfalls setzt es die das Panel
	 * "Neue Lieferung" auf den Startzustand zur�ck.
	 * 
	 * @author Dominik Kl�ter
	 */
	@Override
	public void showCardNeueLieferung() {
		((CardLayout) p_center.getLayout()).show(p_center, "NeueLieferung");
		restMenge.setVisible(false);
		isCardUebersichtAktiv = false;
		isCardNeueLieferungAktiv = true;
		verbleibendeMenge = -1;
		verbleibenderProzentanteil = -1;
		resetCardNeueLieferung();
		gesamtmenge.setText("Gesamtmenge");
	}

	/**
	 * Zeigt das Panel "Lieferungs- & Buchungs�bersicht".
	 */
	@Override
	public void showCardUebersicht() {
		((CardLayout) p_center.getLayout()).show(p_center, "�bersicht");
		isCardUebersichtAktiv = true;
		isCardNeueLieferungAktiv = false;
	}

	/**
	 * Zeigt die Buchungen eines beliebigen Lagers an. Es holt sich, f�r den
	 * Fall, dass es Unterlager hat, alle Buchungen von allen seinen Unterlagen
	 * und zeigt diese dann zusammen mit dem Lagernamen an.
	 * 
	 * @author Philo K�nneker
	 */
	@Override
	public void showLagerbuchungen(Lager l) {
		if (null == getAusgewaehlterKnoten())
			return;
		p_center_lagerbuchungen.removeAll();
		List<Buchung> b = l.getBuchungen();
		if ((l.isLeaf() && b.isEmpty()))
			p_center_lagerbuchungen.add(new JLabel("<html><body>Es wurden noch keine Buchungen ausgef�hrt.<br>Saldo des Lagers \"" + l.getName() + "\": 0"));
		else {
			p_center_lagerbuchungen.setLayout(new BorderLayout());

			if (b.isEmpty())
				b.addAll(getAllBuchungen(l));

			String[] spalten;
			String[][] daten;
			if (l.isLeaf()) {
				int i = 0;
				spalten = new String[] { "Buchungs ID", "Datum", "Menge" };
				daten = new String[b.size()][3];

				for (Buchung bu : b) { // Daten f�r die Tabelle sammeln
					daten[i][0] = ((Integer) bu.getBuchungID()).toString();
					daten[i][1] = sdf.format(bu.getDatum());
					daten[i++][2] = ((Integer) bu.getMenge()).toString();
				}
			} else {
				int i = 0;
				spalten = new String[] { "Buchungs ID", "Lager", "Datum", "Menge" };
				daten = new String[b.size()][4];

				for (Buchung bu : b) { // Gesammelte Daten in den Array �bertragen
					daten[i][0] = ((Integer) bu.getBuchungID()).toString();
					daten[i][1] = bu.getLagerName();
					daten[i][2] = sdf.format(bu.getDatum());
					daten[i++][3] = ((Integer) bu.getMenge()).toString();
				}
			}
			tbl_lagerbuchungen = new JTable(daten, spalten) {
				private static final long serialVersionUID = 6620092595652821138L;

				@Override
				public boolean isCellEditable(int arg0, int arg1) { // Einzelnen Zellen der Tabelle k�nnen nicht mehr bearbeitet werden
					return false;
				}
			};
			tbl_lagerbuchungen.setFillsViewportHeight(true);
			tbl_lagerbuchungen.setColumnSelectionAllowed(false);
			tbl_lagerbuchungen.getTableHeader().setReorderingAllowed(false);
			p_center_lagerbuchungen.add(saldo = new JTextField("Buchungen von " + (l.isLeaf() ? "Lager" : "Oberlagers") + " \"" + l.getName() + "\" mit Saldo "
					+ l.getBestand() + ":"), BorderLayout.NORTH);
			p_center_lagerbuchungen.add(new JScrollPane(tbl_lagerbuchungen), BorderLayout.CENTER);
			saldo.setEditable(false);
			saldo.setFocusable(false);

		}
		p_center_lagerbuchungen.updateUI(); // Anzeige aktualisieren
	}

	/**
	 * Wird, w�hrend das Panel "Neue Lieferung" aktiv ist, ein Lager angeklickt,
	 * so zeigt diese Methode den Lagernamen und das Feld f�r den protzentualen
	 * Anteil an.
	 * 
	 * @author Dominik Kl�ter
	 */
	@Override
	public void showLagerFuerBuchung(String n) {
		resetCardNeueLieferung();
		restMenge.setText("Verbleibende Menge: " + verbleibendeMenge + " entspricht " + verbleibenderProzentanteil + "% der Gesamtmenge");
		lagerBezeichnung.setText(n);
		lagerBezeichnung.setVisible(true);
		prozentAnteil.setVisible(true);
		if (verbleibendeMenge != 0)
			enableJetztBuchen();
		p_center_neue_lieferung_center.updateUI();
	}

	@Override
	public void showLagerverwaltung() {
		lagerverwaltung.setVisible(true);
	}

	/**
	 * Zeigt die Buchungen, die zu der angeklickenten Lieferung (in der Tabelle
	 * in der alle Lieferungen aufgelistet sind), an
	 * 
	 * @author Philo K�nneker
	 */
	@Override
	public void showLieferungsdetails(List<Buchung> buchungsListe) {
		if (buchungsListe.isEmpty())
			return;
		p_center_lieferungdetails.setLayout(new BorderLayout());

		// Aufbau einer 4 x X Matrix
		List<ArrayList<String>> daten = new ArrayList<ArrayList<String>>();
		daten.add(new ArrayList<String>());
		daten.add(new ArrayList<String>());
		daten.add(new ArrayList<String>());
		daten.add(new ArrayList<String>());

		String[] spalten = new String[] { "Buchungs ID", "Lager", "Datum", "Menge" };

		p_center_lieferungdetails.removeAll();

		int i = buchungsListe.size();

		for (Buchung b : buchungsListe) { // Daten f�r Tabelle sammeln
			daten.get(0).add(((Integer) b.getBuchungID()).toString());
			daten.get(1).add(b.getLagerName());
			daten.get(2).add(sdf.format(b.getDatum()));
			daten.get(3).add(((Integer) b.getMenge()).toString());
		}

		if (i > 0) {
			String[][] tblDaten = new String[i][4];

			for (int j = 0; j < i; j++) { // Gesammelte Daten in den Array �bertragen
				tblDaten[j][0] = daten.get(0).get(j);
				tblDaten[j][1] = daten.get(1).get(j);
				tblDaten[j][2] = daten.get(2).get(j);
				tblDaten[j][3] = daten.get(3).get(j);
			}

			tbl_buchungsUebersicht = new JTable(tblDaten, spalten) {
				private static final long serialVersionUID = 861599783877862457L;

				@Override
				public boolean isCellEditable(int arg0, int arg1) { // Einzelnen Zellen der Tabelle k�nnen nicht mehr bearbeitet werden
					return false;
				}
			};

			tbl_buchungsUebersicht.setFillsViewportHeight(true);
			tbl_buchungsUebersicht.setColumnSelectionAllowed(false);
			tbl_buchungsUebersicht.getTableHeader().setReorderingAllowed(false);
			p_center_lieferungdetails.add(new JScrollPane(tbl_buchungsUebersicht), BorderLayout.CENTER);
		}
		p_center_lieferungdetails.updateUI(); // Anzeige aktualisieren
	}

	/**
	 * Zeigt alle get�tigten Lieferungen in einer Tabelle an.
	 * 
	 * @author Philo K�nneker
	 */
	@Override
	public void showLieferungen(List<Lieferung> lieferungen) {
		if (lieferungen.isEmpty()) {
			p_center_lieferungen.add(new JLabel("Es wurden noch keine Lieferungen ausgef�hrt."));
			return;
		}
		p_center_lieferungen.removeAll();
		p_center_lieferungen.setLayout(new BorderLayout());

		int i = 0;

		String[] spalten = new String[] { "Datum", "Buchungsart", "Anzahl Buchungen", "Bestands�nderung" };
		String[][] daten = new String[lieferungen.size()][4];

		for (Lieferung l : lieferungen) { // Daten f�r Tabelle in Array �bertragen
			daten[i][0] = sdf.format(l.getLieferungsDatum());
			daten[i][1] = l.getLieferungsTyp();
			daten[i][2] = ((Integer) l.getBuchungen().size()).toString();
			daten[i][3] = ((Integer) l.getGesamtMenge()).toString();
			i++;
		}

		tbl_lieferungsUebersicht = new JTable(daten, spalten) {
			private static final long serialVersionUID = -6588102605213723085L;

			@Override
			public boolean isCellEditable(int arg0, int arg1) { // Einzelnen Zellen der Tabelle k�nnen nicht mehr bearbeitet werden
				return false;
			}
		};

		tbl_lieferungsUebersicht.setFillsViewportHeight(true);
		tbl_lieferungsUebersicht.setColumnSelectionAllowed(false);
		tbl_lieferungsUebersicht.getTableHeader().setReorderingAllowed(false);
		tbl_lieferungsUebersicht.addMouseListener(listener_LieferungsUebersicht);
		p_center_lieferungen.add(new JLabel("Lieferungs�bersicht: "), BorderLayout.NORTH);
		p_center_lieferungen.add(new JScrollPane(tbl_lieferungsUebersicht), BorderLayout.CENTER);
		p_center_lieferungen.updateUI(); // Anzeige aktualisieren
	}

	@Override
	public void showTabLieferungsBuchungen(List<Buchung> buchungen) {
		theInstance.showLieferungsdetails(buchungen);
		p_center_tabbs.setSelectedComponent(p_center_lieferungdetails);
	}

	@Override
	public void showUndoRedo() {
		undo.setVisible(true);
		redo.setVisible(true);
	}

	@Override
	public void showVerbleibendeMenge() {
		restMenge.setVisible(true);
	}

	@Override
	public void selectTreeRoot() {
		lagerTree.setSelectionRow(0);
		lagerTree.expandRow(0);
	}

	/**
	 * Baut den gesamten JTree (Lagerstruktur) neu auf.
	 */
	@Override
	public void refreshTree() {
		((DefaultTreeModel) lagerTree.getModel()).reload();
	}

	/**
	 * Baut den JTree (Lagerstruktur) ausgehend von dem angegebenen Lager bis in
	 * die zu den Bl�ttern jenes Lagers neu auf.
	 */
	@Override
	public void refreshTree(TreeNode node) {
		((DefaultTreeModel) lagerTree.getModel()).reload(node);
	}

	/**
	 * Gibt die Instanz der Oberfl�che zur�ck. Um ein Mehrfachinitialisierung zu
	 * verhindern, auch wenn dies eher unwahrscheinlich ist, ist diese Methode
	 * 'synchronized'.
	 * 
	 * @author Philo K�nneker
	 * 
	 * @return the singelton object of the class 'Oberflaeche'
	 */
	public static synchronized Oberflaeche getInstance() {
		if (null == theInstance) {
			theInstance = new OberflaecheImpl();
		}
		return theInstance;
	}

	public static void setLagerListener(ActionListener l) {
		listener_Lagerverwaltung = l;
	}

	public static void setLieferungListener(MouseListener l) {
		listener_LieferungsUebersicht = l;
	}

	/**
	 * �berschreibt die Methode clone(), welche von java.lang.Object geerbt
	 * wird. Wirft immer eine CloneNotSupportedException, da dies ein Singelton
	 * ist.
	 * 
	 * @author Philo K�nneker
	 * 
	 * @throws CloneNotSupportedException
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("This is a singelton, dude! No cloning allowed!");
	}

}
