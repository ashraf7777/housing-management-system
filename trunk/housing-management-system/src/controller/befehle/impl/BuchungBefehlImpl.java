package controller.befehle.impl;

import java.util.Date;
import java.util.Stack;

import model.Buchung;
import model.Unit;
import view.Oberflaeche;
import view.Tools;
import view.impl.OberflaecheImpl;
import controller.befehle.IBuchungBefehl;

/**
 * Implementiert das Interface f�r den Buchungsbefehl.
 * 
 * @version 1.1.0
 * @author Philo K�nneker
 * 
 */
public class BuchungBefehlImpl implements IBuchungBefehl {

	/**
	 * F�hrt die Schritte, die f�r eine neue Buchung n�tig sind, aus.
	 */
	@Override
	public int execute(Unit l, int menge, Date d) {
		Buchung b;
		int diff = l.veraenderBestand(menge);
		if (diff == menge) // Falls die zur�ckgegebene Differnz mit der Buchungsmenge identisch ist, wird keine Buchung ausgef�hrt
			return diff;
		l.addBuchung(b = new Buchung(menge + diff, d, l, prozent + ((diff > 0) ? (int) (menge / (float) prozent) : 0)));
		
		return diff;
	}
}
