package jPhotoLibrary;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

class JPL_TreeNode extends DefaultMutableTreeNode {
		
	private static final long serialVersionUID = 3177477604739901338L;
	
	public JPL_TreeNode(Object userObject) {
		super(userObject);				
	}
	
	public JPL_TreeNode getFileParentNode(JPL_TreeNode parentsSetNode, String parent) {
		for (TreeNode treeNode : parentsSetNode.children) {
			if (treeNode.toString() == parent) {
				return (JPL_TreeNode) treeNode;
			};
		};
		return parentsSetNode;
	}
	
}
