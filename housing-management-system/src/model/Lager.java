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
public class Lager extends DefaultMutableTreeNode {

	private static Lager wurzel;
	private static List<String> namensListe = new ArrayList<String>();
	private Lager blatt;
	private int bestand;
	private boolean isBestandHaltend;
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
	public Lager(String bez) {
		super(bez + " 0");
		if (checkNamen(bez)) {
			namensListe.add(bez);
			this.name = bez;
			this.bestand = 0;
			this.isBestandHaltend = true;
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
	public Lager addTreeElement(String bez) {
		blatt = new Lager(bez);
		this.add(blatt);
		this.isBestandHaltend = false; // �bergeordneter Knoten darf keinen Bestand zeigen - falls diese Methode an einem Blatt aufgerufen wurde, ist dieses ebenfalls nicht mehr f�hig einen Bestand zu halten und anzuzeigen

		this.setUserObject(this.name); // �bergeordneten Knoten umbenennen, sodass der Bestand nicht mehr angezeigt wird

		return blatt;
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
	 * Versucht den Bestand des Lagers zu �ndern. Sollten Fehler auftreten,
	 * werden LagerVerwaltungsExceptions geworfen.
	 * 
	 * @param menge
	 * @return diff Positive Differenz zwischen der Menge, die abgebucht werden
	 *         soll und dem Lagersaldo. Bei Zubuchungen liefert es 0.
	 * @throws LagerverwaltungsException
	 *             Wenn ein Fehler beim �ndern auftreten.
	 */
	public int veraenderBestand(int menge) {
		if (this.bestand == 0 && menge < 0) {
			List<String> result = new ArrayList<String>();
			result.add("Bestand kleiner 0 nicht m�glich."); // Abbuchung aus einem leeren Lager ist nicht m�glich
			throw new LagerverwaltungsException("Bestand vom Lager \"" + this.name + "\" kann nicht ge�ndert werden.", result, null);
		}
		if ((this.bestand + menge >= 0)) {
			this.bestand = this.bestand + menge;
			this.setUserObject(this.name + " " + this.bestand); // �ndert angezeigten Namen im Baum
			return 0;
		} else {
			int diff = Math.abs(this.bestand + menge); // Nimmt die Differenz aus dem abzubuchenden Bestand und dem abbuchbaren Bestand
			this.bestand = 0;
			this.setUserObject(this.name + " 0"); // �ndert angezeigten Namen im Baum
			return diff;
		}
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
			this.setUserObject(this.isBestandHaltend ? name + " " + this.bestand : name); // �ndert angezeigten Namen im Baum
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
	 * Gibt den eigenen oder oder kumulierten Bestand aller Unterlager zur�ck
	 * 
	 * @return der kumulierte Bestand aller Unterlager
	 */
	public int getBestand() {
		int bestand_summe = 0;

		if (this.isLeaf()) { // falls dieser Knoten keine Kinder hat
			return this.bestand;
		} else {
			// Best�nde der einzelnen Kinder/Bl�tter zusammenaddieren (kummulierter Bestand der Unterlager)
			for (int i = 0; i < this.getChildCount(); i++) {
				bestand_summe = bestand_summe + ((Lager) this.getChildAt(i)).getBestand();
			}
			return bestand_summe;
		}
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
	 * Gibt den eigenen Bestand dieses Lagers zur�ck
	 * 
	 * @return Bestand dieses Lagers
	 */
	public int getEinzelBestand() {
		return this.bestand;
	}

	/**
	 * Setzt den Bestand eines Lagers, insofern das Lager einen Bestand haben
	 * darf und die �bergebene Menge gr��er oder gleich 0 ist.
	 * 
	 * Wird nicht mehr verwendet - stattdessen wird die Methode
	 * veraenderBestand(int) verwendet
	 * 
	 * @see veraenderBestand(int)
	 * 
	 * @deprecated since Version 1.1.0
	 * @param menge
	 * @throws LagerverwaltungsException
	 */
	@Deprecated
	public void setBestand(int menge) {
		List<String> result = new ArrayList<String>();
		if (this.isBestandHaltend) {
			if (menge >= 0) {
				this.bestand = menge;
				return;
			} else {
				result.add("Bestand kann nicht kleiner als 0 sein.");
				throw new LagerverwaltungsException("Bestand vom Lager \"" + this.name + "\" kann nicht gesetzt werden", result, null);
			}
		} else {
			result.add("Lager \"" + this.name + "\" kann keinen Bestand haben.");
			throw new LagerverwaltungsException("Lager kann keinen Bestand haben.", result, null);
		}
	}

	/**
	 * Methode um herauszufinden, ob dieses Lager einen Bestand haben kann, oder
	 * nicht.
	 * 
	 * @return true, wenn dieses Lager einen Bestand halten kann.
	 */
	public boolean isBestandHaltend() {
		return this.isBestandHaltend;
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
	public static Lager getTree() {
		return wurzel;
	}

	/**
	 * Erstellt das Wurzelelement f�r die Lagerbaumstruktur.
	 * 
	 * @param bez
	 *            Der Name des Wurzelelement.
	 * @return Das Wurzelelement.
	 */
	public static Lager addWurzel(String bez) {
		wurzel = new Lager(bez);
		wurzel.name = bez;
		return wurzel;
	}
}
