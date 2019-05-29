package jPhotoLibrary;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

class JPL_TreeModel extends DefaultTreeModel {

	private static final long serialVersionUID = -2763187484465747888L;

	public JPL_TreeModel(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
	}
	
}
