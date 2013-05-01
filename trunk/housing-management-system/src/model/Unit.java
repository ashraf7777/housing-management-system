package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import exception.LagerverwaltungsException;

/**
 * Stellt ein Lager mit einem Namen, gegebenfalls einem Bestand und gegebenfalls
 * untergeordneten Lagern (Blättern) dar. Des Weiteren enthält diese Klasse das
 * Wurzelelement der Lagerstruktur und eine Liste aller vergebenen Lagernamen.
 * 
 * @version 1.1.0
 * @author Dominik Klüter
 * 
 */
public class Unit extends DefaultMutableTreeNode {

	private static Unit root;
	private static List<String> namensListe = new ArrayList<String>();
	private Unit leaf;
	private float area;
	private float pricePerNight;
	private float pricePerMonth;
	private boolean isOccupied;
	private String name;

	private final List<Buchung> buchungen = new ArrayList<Buchung>();

	private static final long serialVersionUID = -6664495404957376450L;

	/**
	 * Erstellt ein neues Lager und fügt den Namen des neuen Lagers der Liste
	 * aller Lagernamen hinzu
	 * 
	 * @param bez
	 *            Der Name des Lagers
	 */
	public Unit(String bez) {
		super(bez);
		if (checkNamen(bez)) {
			namensListe.add(bez);
			this.name = bez;
			this.isOccupied = true;
		} else {
			List<String> result = new ArrayList<String>();
			result.add("Der Lagername \"" + bez + "\" wurde bereits verwendet!");
			throw new LagerverwaltungsException("Lagername bereits verwendet!", result, null);
		}
	}

	/**
	 * Fügt dem Baum ein neues Element hinzu und gibt dieses Lager dann zurück.
	 * 
	 * @param bez
	 *            Name des Elements (Lagername)
	 * @return Das soeben erstellte Lager
	 */
	public Unit addTreeElement(String bez) {
		leaf = new Unit(bez);
		this.add(leaf);
		this.isOccupied = false; // übergeordneter Knoten darf keinen Bestand zeigen - falls diese Methode an einem Blatt aufgerufen wurde, ist dieses ebenfalls nicht mehr fähig einen Bestand zu halten und anzuzeigen

		return leaf;
	}

	/**
	 * Fügt eine neue Buchung der Liste der Buchungen dieses Lagers hinzu.
	 * 
	 * @param b
	 *            Die hinzuzufügende Buchung
	 * @return true, wenn die Buchung erfolgreich hinzugefügt wurde.
	 */
	public boolean addBuchung(Buchung b) {
		return buchungen.add(b);
	}

	/**
	 * Überprüfung, ob der Lagername bereits in Verwendung ist
	 * 
	 * @param bez
	 *            Zu überprüfender Name.
	 * @return true, wenn der Name verwendet werden kann.
	 */
	private boolean checkNamen(String bez) {
		if (namensListe.isEmpty())
			return true;
		else {
			for (String name : namensListe) {
				if (bez.equals(name))
					return false;
			}
		}
		return true;
	}

	
	/**
	 * Verändert den Namen eines Lagers, insofern nicht ein anderes Lager diesen
	 * Namen inne hat.
	 * 
	 * @param name
	 *            Der neue Name.
	 */
	public void veraendereName(String name) {
		if (this.name.equalsIgnoreCase(name))
			return;
		if (checkNamen(name)) {
			namensListe.remove(this.name);
			this.name = name;
			namensListe.add(name);
			return;
		} else {
			List<String> result = new ArrayList<String>(); // Falls der Name bereits vergeben ist, wird hier eine Exception geworfen
			result.add("Ein Lager mit diesem Name existiert bereits.");
			throw new LagerverwaltungsException("Der Name konnte nicht geändert werden.", result, null);
		}
	}

	/**
	 * Entfernt eine bestimmtes Buchungsobjekt aus der Liste aller Buchungen auf
	 * diesem Lager. Insbesondere ist dies für die undo/redo-Funktionalität
	 * wichtig.
	 * 
	 * @param b
	 *            Die zu entferndene Buchung
	 * @return true wenn die Liste das zu entfernde Element enthält
	 */
	public boolean removeBuchung(Buchung b) {
		return buchungen.remove(b);
	}

	/**
	 * Gibt eine List<Buchung> mit allen Buchungen, die auf diesem Lager gebucht
	 * wurden, zurück.
	 * 
	 * @return Die Liste aller Buchungen des Lagers.
	 */
	public List<Buchung> getBuchungen() {
		return buchungen;
	}


	/**
	 * Methode um herauszufinden, ob dieses Lager einen Bestand haben kann, oder
	 * nicht.
	 * 
	 * @return true, wenn dieses Lager einen Bestand halten kann.
	 */
	public boolean isBestandHaltend() {
		return this.isOccupied;
	}

	/**
	 * Gibt den Namen des Lagers zurück.
	 * 
	 * @return Der Name des Lagers
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die gesamte Lagerstruktur als ein Lager zurück.
	 * 
	 * @return Das Wurzellager
	 */
	public static Unit getTree() {
		return root;
	}

	/**
	 * Erstellt das Wurzelelement für die Lagerbaumstruktur.
	 * 
	 * @param bez
	 *            Der Name des Wurzelelement.
	 * @return Das Wurzelelement.
	 */
	public static Unit addWurzel(String bez) {
		root = new Unit(bez);
		root.name = bez;
		return root;
	}
}
