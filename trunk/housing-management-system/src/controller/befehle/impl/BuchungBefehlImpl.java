package controller.befehle.impl;

import java.util.Date;
import java.util.Stack;

import model.Buchung;
import model.Lager;
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

	private final Stack<Buchung> buchungsStackUndo = new Stack<Buchung>();
	private final Stack<Buchung> buchungsStackRedo = new Stack<Buchung>();
	private final Stack<Lager> lagerStackUndo = new Stack<Lager>();
	private final Stack<Lager> lagerStackRedo = new Stack<Lager>();

	/**
	 * F�hrt die Schritte, die f�r eine neue Buchung n�tig sind, aus.
	 */
	@Override
	public int execute(Lager l, int menge, Date d, int prozent) {
		Buchung b;
		int diff = l.veraenderBestand(menge);
		if (diff == menge) // Falls die zur�ckgegebene Differnz mit der Buchungsmenge identisch ist, wird keine Buchung ausgef�hrt
			return diff;
		l.addBuchung(b = new Buchung(menge + diff, d, l, prozent + ((diff > 0) ? (int) (menge / (float) prozent) : 0)));
		buchungsStackUndo.push(b);
		lagerStackUndo.push(l);
		return diff;
	}

	/**
	 * Macht die zuletzt get�tigte Buchung r�ckg�ngig, insofern eine solche
	 * Buchung existiert, die r�ckg�ngig gemacht werden kann.
	 */
	@Override
	public void undo() {
		try {
			Oberflaeche gui = OberflaecheImpl.getInstance();
			Buchung b = buchungsStackRedo.push(buchungsStackUndo.pop());
			Lager l = lagerStackRedo.push(lagerStackUndo.pop());
			l.removeBuchung(b);
			l.veraenderBestand(-b.getMenge());
			Buchung.getNeueBuchungen().remove(b);
			gui.showLagerFuerBuchung(l.getName());
			gui.setVerbleibendeMenge(gui.getVerbleibendeMenge() + (gui.isAbBuchung() ? -b.getMenge() : b.getMenge()));
			gui.setVerbleibenderProzentanteil(gui.getVerbleibenderProzentanteil() + b.getProzentAnteil());
			gui.showLagerFuerBuchung(gui.getAusgewaehlterKnoten() != null ? gui.getAusgewaehlterKnoten().getName() : "Lagerverwaltung");
		} catch (Exception e) {
			// Sollte normalerweise nicht mehr ben�tigt werden :)
			// Nur f�r den Fall, dass irgendetwas schief l�uft
			// Zeile 55 produzierte fr�her eine NullPointerException, deshalb dieser catch-Block
			Tools.showMsg("Sie haben gewonnen!\n\nUnd zwar ein Stickstoffatom.\nWir gratulieren Ihnen herzlichst! :)\n\n" + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Wiederholt die zuletzt r�ckg�ngig gemachte Buchung.
	 */
	@Override
	public void redo() {
		try {
			Oberflaeche gui = OberflaecheImpl.getInstance();
			Buchung b = buchungsStackUndo.push(buchungsStackRedo.pop());
			Lager l = lagerStackUndo.push(lagerStackRedo.pop());
			l.veraenderBestand(b.getMenge());
			l.addBuchung(b);
			Buchung.getNeueBuchungen().add(b);
			gui.setVerbleibendeMenge(gui.getVerbleibendeMenge() - (gui.isAbBuchung() ? -b.getMenge() : b.getMenge()));
			gui.setVerbleibenderProzentanteil(gui.getVerbleibenderProzentanteil() - b.getProzentAnteil());
			gui.showLagerFuerBuchung(gui.getAusgewaehlterKnoten() != null ? gui.getAusgewaehlterKnoten().getName() : "Lagerverwaltung");
		} catch (Exception e) {
			// Sollte normalerweise nicht mehr ben�tigt werden :)
			// Nur f�r den Fall, dass irgendetwas schief l�uft
			// Zeile 80 produzierte fr�her eine NullPointerException, deshalb dieser catch-Block
			Tools.showMsg("Sie haben gewonnen!\n\nUnd zwar ein Sauerstoffatom.\nWir gratulieren Ihnen herzlichst! :)");
		}
	}

	/**
	 * Rollt alle get�tigten Buchungen der aktuellen neuen Lieferung zur�ck auf
	 * den Ausgangszustand.
	 */
	@Override
	public void undoAll() {
		while (!buchungsStackUndo.isEmpty() || !lagerStackUndo.isEmpty()) {
			undo();
		}
		clearAll();
	}

	/**
	 * Leer alle Stacks und die Liste von neuen Buchungen in Buchung, um eine
	 * neue Lieferung zu erm�glichen.
	 */
	@Override
	public void clearAll() {
		buchungsStackUndo.clear();
		buchungsStackRedo.clear();
		lagerStackUndo.clear();
		lagerStackRedo.clear();
		Buchung.clearNeueBuchungen();
	}

	/**
	 * L�scht den Stack, der alle m�glichen wiederholbaren Buchungen enth�lt.
	 * Wird aufgerufen, sobald eine neue Buchung get�tigt wurde.
	 */
	@Override
	public void clearRedos() {
		buchungsStackRedo.clear();
		lagerStackRedo.clear();
	}

	/**
	 * @return true, wenn Buchungen r�ckg�ngig gemacht werden k�nnen
	 */
	@Override
	public boolean hasRemainingUndos() {
		return buchungsStackUndo.size() != 0;
	}

	/**
	 * @return true, wenn r�ckg�ngig gemachte Buchungen wiederholt werden
	 *         k�nnen.
	 */
	@Override
	public boolean hasRemainingRedos() {
		return buchungsStackRedo.size() != 0;
	}

}
