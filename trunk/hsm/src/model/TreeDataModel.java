package model;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * This class provides the tree structure and stores the belonging units to the
 * nodes.
 * 
 * @author D20018
 * 
 */
public class TreeDataModel extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	Unit unit;
	public static TreeDataModel root;

	/**
	 * Standard constructor
	 */
	public TreeDataModel() {
		super();
	}

	/**
	 * Customized constructor to create a new tree element and store the
	 * belonging unit at one time
	 * 
	 * @param unit
	 */
	public TreeDataModel(Unit unit) {
		this.unit = unit;
		this.setUserObject(unit);
	}

	/**
	 * Overriden customized function to gain the unit's name
	 */
	@Override
	public String toString() {
		return unit.getName();
	}

	/**
	 * Add a child to an other tree element to gain a hierarchical structure
	 */
	@Override
	public void add(MutableTreeNode newChild) {
		super.add(newChild);
		((Unit) this.getUserObject()).setHasChild(true);
	}
}
