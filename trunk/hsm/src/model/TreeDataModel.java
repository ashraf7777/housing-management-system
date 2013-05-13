package model;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeDataModel extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Unit unit;
	
	public TreeDataModel()
	{
		super();
	}
	
	public TreeDataModel(Unit unit)
	{
		this.unit = unit;
		this.setUserObject(unit);
	}
	//Erstellte Units dem TreeDataModel hinzufügen um Trennung zwischen dem Tree und den Units zu erreichen
	
}
