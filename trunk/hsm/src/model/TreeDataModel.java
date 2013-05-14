package model;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeDataModel extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1L;
	Unit unit;
	public static TreeDataModel root;
	
	public TreeDataModel()
	{
		super();
	}
	
	public TreeDataModel(Unit unit)
	{
		this.unit = unit;
		this.setUserObject(unit);
	}
	
	@Override
	public String toString() {
		return unit.getName();
	}
}
