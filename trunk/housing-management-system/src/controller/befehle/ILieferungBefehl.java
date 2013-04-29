package controller.befehle;

import java.util.Date;
import java.util.List;

import model.Buchung;
import model.Lieferung;

/**
 * Interface f�r den Lieferungsbefehl
 * 
 * @version 1.1.0
 * @author Dominik Kl�ter
 * 
 */
public interface ILieferungBefehl {

	public abstract Lieferung execute(Date d, int menge, String typ, List<Buchung> buchungen);

}