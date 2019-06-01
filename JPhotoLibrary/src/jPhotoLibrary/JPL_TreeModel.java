package jPhotoLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

class JPL_TreeModel extends DefaultTreeModel {
	
	private static final long serialVersionUID = -2763187484465747888L;
	private Map<String, Map<String, JPL_TreeNode>> parentsSetHM = new HashMap<>(); 
	private JPL_TreeNode root;
	private enum PARENT_SET_NODE {NODE};
	
	public JPL_TreeModel(JPL_TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		this.root = root;
	}
	
	public void add(Set<JPL_File> sameChecksumSet) {
		
		//find unique parents
		Set<String> parentsHS = new HashSet<>();
		for (JPL_File file : sameChecksumSet) {
			parentsHS.add(file.getParent());			
		}
		//sort parents
		List<String> parentsList = new ArrayList<>();
		for (String string : parentsHS) {
			parentsList.add(string);		
		};
		parentsList.sort(null);
		//make hash string of sorted  file parents set
		String parentsSetHashString = parentsList.toString();
		//find or create node for file parents set
		Map<String, JPL_TreeNode> parentsHM;
		if (parentsSetHM.containsKey(parentsSetHashString)) {
			parentsHM = parentsSetHM.get(parentsSetHashString);		
		} 
		else {
			parentsHM = new HashMap<>();
			JPL_TreeNode parentsSetNode = new JPL_TreeNode(parentsSetHashString);
			parentsHM.put(PARENT_SET_NODE.NODE.toString(), parentsSetNode);
			parentsSetHM.put(parentsSetHashString, parentsHM);
			root.add(parentsSetNode);			
		};
		//add files to the tree
		for (JPL_File file : sameChecksumSet) {
			String parent = file.getParent();
			//get file parent node
			JPL_TreeNode parentNode;
			if (parentsHM.containsKey(parent)) {
				parentNode = parentsHM.get(parent);								
			}
			else {
				parentNode = new JPL_TreeNode(parent);
				JPL_TreeNode parentsSetNode = parentsHM.get(PARENT_SET_NODE.NODE.toString());
				parentsSetNode.add(parentNode);
				parentsHM.put(file.getParent(), parentNode);
			};			
			JPL_TreeNode fileNode = new JPL_TreeNode(file.getPath());
			fileNode.setAllowsChildren(false);
			parentNode.add(fileNode);						
		}		
	}
	
}
