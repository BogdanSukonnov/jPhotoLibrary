package jPhotoLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

class JPL_TreeModel extends DefaultTreeModel {
	
	private static final long serialVersionUID = -2763187484465747888L;
	private Map<String, JPL_TreeNode> parentsHM = new HashMap<>(); 
	private JPL_TreeNode root;
	
	public JPL_TreeModel(JPL_TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		this.root = root;
	}
	
	public void add(Set<JPL_File> sameChecksumSet) {
		List<String> parentsSorted= new ArrayList<>();
		for (JPL_File file : sameChecksumSet) {
			parentsSorted.add(file.getParent());			
		}
		parentsSorted.sort(null);
		String parentsSetHashString = "";
		for (String parent : parentsSorted) {
			parentsSetHashString += parent;		
		};
		JPL_TreeNode parentsSetNode = parentsHM.getOrDefault(parentsSetHashString, new JPL_TreeNode(parentsSetHashString));
		parentsHM.putIfAbsent(parentsSetHashString, parentsSetNode);
		if (root.getIndex(parentsSetNode) == -1) {
			root.add(parentsSetNode);
		};
		if (parentsSetNode.getChildCount() == 0) {
			for (String parent : parentsSorted) {
				parentsSetNode.add(new JPL_TreeNode(parent));	
			};						
		};
		for (JPL_File file : sameChecksumSet) {
			JPL_TreeNode parentNode = parentsSetNode.getFileParentNode(parentsSetNode, file.getParent());
			JPL_TreeNode fileNode = new JPL_TreeNode(file.getPath());
			fileNode.setAllowsChildren(false);
			parentNode.add(fileNode);			
		}		
	}
	
}
