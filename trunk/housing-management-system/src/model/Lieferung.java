package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Stellt eine Lieferung mit ihren Buchungen, einem Lieferungsdatum, einer
 * Gesamtmenge und dem Lieferungstyp dar. Des Weiteren enth�lt diese Klasse eine
 * Liste aller Lieferungen.
 * 
 * @version 1.1.0
 * @author Philo K�nneker
 * 
 */
public class Lieferung {
	private static List<Lieferung> lieferungen = new ArrayList<Lieferung>();
	private final Date lieferungsDatum;
	private final List<Buchung> buchungen;
	private final int gesamtMenge;
	private final String lieferungsTyp;

	/**
	 * Erstellt eine neue Lieferung und f�gt diese automatisch der Liste aller
	 * Lieferungen hinzu.
	 * 
	 * @param d
	 *            Das exakte Datum dieser Lieferung
	 * @param gesamtMenge
	 *            Die kummulierte Menge aller Buchungen f�r diese Lieferung
	 * @param typ
	 *            Buchungsart, z.B. "Zubuchung", "Abbuchung"
	 * @param b
	 *            Liste mit allen Buchungen, die zu dieser Lieferung geh�ren
	 */
	public Lieferung(Date d, int gesamtMenge, String typ, List<Buchung> b) {
		this.lieferungsDatum = d;
		this.gesamtMenge = gesamtMenge;
		this.lieferungsTyp = typ;
		this.buchungen = new ArrayList<Buchung>(b);
		lieferungen.add(this);
	}

	/**
	 * Gibt eine List<Buchung> mit allen Buchungen dieser Lieferung zur�ck.
	 * 
	 * @return Die Liste aller Buchungen dieser Lieferung
	 */
	public List<Buchung> getBuchungen() {
		return buchungen;
	}

	/**
	 * Gibt die Kummulierte Menge aller Buchungen aus dieser Lieferung zur�ck.
	 * 
	 * @return die gesamte Menge dieser Lieferung
	 */
	public int getGesamtMenge() {
		return gesamtMenge;
	}

	/**
	 * Gibt das genaue Datum der Lieferung zur�ck
	 * 
	 * @return Das Lieferdatum
	 */
	public Date getLieferungsDatum() {
		return this.lieferungsDatum;
	}

	/**
	 * 
	 * F�gt eine neue Lieferung der Liste aller Lieferungen hinzu und gibt die
	 * neue Liste anschlie�end zur�ck.
	 * 
	 * @param l
	 *            Die hinzuzuf�gende Lieferung
	 * @return true, wenn die Lieferung der Liste hinzugef�gt wurde.
	 */
	public static boolean addLieferungen(Lieferung l) {
		return lieferungen.add(l);
	}

	/**
	 * Liefert eine List<Lieferung> mit allen bereits get�tigten Lieferungen und
	 * den darin enthalteten Buchungen zur�ck. Dabei wird nicht zwischen Zu- und
	 * Abbuchung unterschieden.
	 * 
	 * @return Liste aller Lieferungen
	 */
	public static List<Lieferung> getAllLieferungen() {
		return lieferungen;
	}

	/**
	 * Da die Lieferungen eindeutig an ihrem Datum identifiziert werden k�nnen,
	 * kann damit die entsprechende Lieferung gefunden und zur�ckgegeben werden.
	 * 
	 * @param date
	 *            Datum im Format "dd.MM.yyyy - hh:mm:ss"
	 * @return Die passende Lieferung zu dem Datum date
	 */
	public static Lieferung getLieferung(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss");
		for (Lieferung l : lieferungen) {
			if (date.equals(sdf.format(l.getLieferungsDatum())))
				return l;
		}
		return null;
	}

	/**
	 * Liefert den Typ der Lieferung zur�ck. Bsp.: Zubuchung oder Abbuchung
	 * 
	 * @return Typ der Lieferung
	 */
	public String getLieferungsTyp() {
		return lieferungsTyp;
	}
}
