package controller.befehle;

import java.util.Date;

import model.Unit;

/**
 * Interface f�r den Buchungsbefehl
 * 
 * @version 1.1.0
 * @author Dominik Kl�ter
 * 
 */
public interface IBuchungBefehl {

	public int execute(Unit l, int menge, Date d, int prozent);

	public void undo();

	public void redo();

	public void undoAll();

	public void clearAll();

	public void clearRedos();

	public boolean hasRemainingUndos();

	public boolean hasRemainingRedos();

}