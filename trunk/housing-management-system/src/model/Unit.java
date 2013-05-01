package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import exception.LagerverwaltungsException;

/**
 * Stellt ein Lager mit einem Namen, gegebenfalls einem Bestand und gegebenfalls
 * untergeordneten Lagern (Bl�ttern) dar. Des Weiteren enth�lt diese Klasse das
 * Wurzelelement der Lagerstruktur und eine Liste aller vergebenen Lagernamen.
 * 
 * @version 1.1.0
 * @author Dominik Kl�ter
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
	 * Erstellt ein neues Lager und f�gt den Namen des neuen Lagers der Liste
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
	 * F�gt dem Baum ein neues Element hinzu und gibt dieses Lager dann zur�ck.
	 * 
	 * @param bez
	 *            Name des Elements (Lagername)
	 * @return Das soeben erstellte Lager
	 */
	public Unit addTreeElement(String bez) {
		leaf = new Unit(bez);
		this.add(leaf);
		this.isOccupied = false; // �bergeordneter Knoten darf keinen Bestand zeigen - falls diese Methode an einem Blatt aufgerufen wurde, ist dieses ebenfalls nicht mehr f�hig einen Bestand zu halten und anzuzeigen

		return leaf;
	}

	/**
	 * F�gt eine neue Buchung der Liste der Buchungen dieses Lagers hinzu.
	 * 
	 * @param b
	 *            Die hinzuzuf�gende Buchung
	 * @return true, wenn die Buchung erfolgreich hinzugef�gt wurde.
	 */
	public boolean addBuchung(Buchung b) {
		return buchungen.add(b);
	}

	/**
	 * �berpr�fung, ob der Lagername bereits in Verwendung ist
	 * 
	 * @param bez
	 *            Zu �berpr�fender Name.
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
	 * Ver�ndert den Namen eines Lagers, insofern nicht ein anderes Lager diesen
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
			throw new LagerverwaltungsException("Der Name konnte nicht ge�ndert werden.", result, null);
		}
	}

	/**
	 * Entfernt eine bestimmtes Buchungsobjekt aus der Liste aller Buchungen auf
	 * diesem Lager. Insbesondere ist dies f�r die undo/redo-Funktionalit�t
	 * wichtig.
	 * 
	 * @param b
	 *            Die zu entferndene Buchung
	 * @return true wenn die Liste das zu entfernde Element enth�lt
	 */
	public boolean removeBuchung(Buchung b) {
		return buchungen.remove(b);
	}

	/**
	 * Gibt eine List<Buchung> mit allen Buchungen, die auf diesem Lager gebucht
	 * wurden, zur�ck.
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
	 * Gibt den Namen des Lagers zur�ck.
	 * 
	 * @return Der Name des Lagers
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die gesamte Lagerstruktur als ein Lager zur�ck.
	 * 
	 * @return Das Wurzellager
	 */
	public static Unit getTree() {
		return root;
	}

	/**
	 * Erstellt das Wurzelelement f�r die Lagerbaumstruktur.
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
