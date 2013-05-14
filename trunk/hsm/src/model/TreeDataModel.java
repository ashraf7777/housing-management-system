package model;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

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
	
	@Override
	public void add(MutableTreeNode newChild)
	{
		super.add(newChild);
		((Unit)this.getUserObject()).setHasChild(true);
	}
}
