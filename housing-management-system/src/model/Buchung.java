package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Stellt eine Buchung mit einer Buchungsmenge, dem prozentualen Anteil der
 * Gesamtmenge, einer eindeutigen Identifikationsnummer, dem Buchungsdatum, und
 * dem Lager, auf dem die jeweilige Buchung ausgef�hrt worden ist. Des Weiteren
 * enth�lt diese Klasse eine Liste, die zur Laufzeit Buchungen enth�lt, die
 * gerade in der �bersicht "Neue Lieferung" erstellt wurden, aber die Lieferung
 * als Ganzes noch nicht best�tigt und abgeschlossen worden ist.
 * 
 * 
 * @version 1.1.0
 * @author Philo K�nneker
 * 
 */
public class Buchung {

	private static int id;
	private static List<Buchung> neueBuchungen = new ArrayList<Buchung>();

	private final int menge, prozentAnteil;
	private final int buchung_ID;
	private final Lager lager;
	private Date datum;

	/**
	 * Erstellt eine neue Buchung mit einer zu buchenden Menge, dem prozentualen
	 * Anteil von der Gesamtmenge in Prozent, eine Referenz auf das Lager, auf
	 * dem diese Buchung ausgef�hrt wurde, dem genauen Datum und einer ebenfalls
	 * eindeutigen ID.
	 * 
	 * @param m
	 *            Die zu buchende Menge
	 * @param datum
	 *            Das Datum der Buchung.
	 * @param l
	 *            Das Lager, auf der die Buchung berechnet wird
	 * @param prozent
	 *            Der Prozentanteil an der Gesamtmenge.
	 */
	public Buchung(int m, Date datum, Lager l, int prozent) {
		this.menge = m;
		this.prozentAnteil = prozent;
		this.lager = l;
		this.buchung_ID = getNextId();
		this.datum = datum;
		neueBuchungen.add(this);
	}

	/**
	 * Wird ausschlie�lich in controller.befehle.impl.LieferungBefehlImpl
	 * aufgerufen, da dort das Datum auf den Zeitpunkt, an dem alle Buchungen
	 * best�tigt werden, gesetzt wird.
	 * 
	 * @param d
	 *            Das Datum an dem die Lieferung ausgef�hrt und best�tigt wird.
	 */
	public void updateDate(Date d) {
		this.datum = d;
	}

	public int getBuchungID() {
		return buchung_ID;
	}

	public Date getDatum() {
		return datum;
	}

	public String getLagerName() {
		return this.lager.getName();
	}

	public int getMenge() {
		return this.menge;
	}

	public int getProzentAnteil() {
		return prozentAnteil;
	}

	/**
	 * Gibt eine List<Buchung> aller neuen, noch nicht best�tigter Buchungen
	 * zur�ck.
	 * 
	 * @return Die Liste aller noch nicht best�tigter Buchungen.
	 */
	public static List<Buchung> getNeueBuchungen() {
		return neueBuchungen;
	}

	/**
	 * Berechnet die Menge, die alle neuen, noch nicht best�tigten Buchungen
	 * zusammen haben.
	 * 
	 * @return Die Gesamtmenge aller neuen Buchungen.
	 */
	public static int getGesamtMenge() {
		int m = 0;
		for (Buchung b : neueBuchungen) {
			m += b.getMenge();
		}
		return m;
	}

	/**
	 * Wird aufgerufen, wenn die Lieferung mit allen Buchungen best�tigt worden
	 * ist. L�scht alle neuen Buchungen aus der Liste aller neuen Buchungen.
	 */
	public static void clearNeueBuchungen() {
		neueBuchungen.clear();
	}

	/**
	 * Gibt die n�chste eindeutige ID f�r eine Buchung zur�ck. Diese Methode ist
	 * synchronized um einen mehrfachen gleichzeitigen Zugriff und eine damit
	 * verbundene Inkonsistenz zu verhindern.
	 * 
	 * @return Die n�chste eindeutige ID
	 */
	private static synchronized int getNextId() {
		return ++id;
	}
}
