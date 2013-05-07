package controller.befehle;

import java.util.Date;

import model.Unit;

/**
 * Interface für den Buchungsbefehl
 * 
 * @version 1.1.0
 * @author Dominik Klüter
 * 
 */
public interface IBuchungBefehl {

	public int execute(Unit l, int menge, Date d);

	

}