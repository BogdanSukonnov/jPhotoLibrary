package jPhotoLibrary;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JPhotoLibrary {
	
	private String[] foldersToScan;
	final String thisPC = "PC1"; //TODO This PC
	private Map<Long, Set<JPL_File>> localLengthsHM = new HashMap<>();
	private Map<String, Set<JPL_File>> localChecksumHM = new HashMap<>();
	private Map<String, Set<JPL_File>> localDuplicatesHM = new HashMap<>();
	private Set<JPL_File> filesHS = new HashSet<>();
	private JPL_TreeModel treeModel = new JPL_TreeModel(new JPL_TreeNode(new String("root node")), true);
		
	public static void main(String[] args) {
		JPhotoLibrary jPhotoLibrary = new JPhotoLibrary();
		jPhotoLibrary.start();
	}
	
	private JPhotoLibrary() {				
	}
	
	private void start() {
		setDirectoriesToScan();
		openMainFrame();		
	}
	
	private void openMainFrame() {		
		EventQueue.invokeLater(new Runnable() {
			JPhotoLibrary jPhotoLibrary;
			Runnable init(JPhotoLibrary jPhotoLibrary) {
				this.jPhotoLibrary = jPhotoLibrary;
				return this;
			};
			public void run() {
				try {
					MainFrame frame = new MainFrame(treeModel, jPhotoLibrary);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.init(this));		
	}
	
	void setDirectoriesToScan() {
		foldersToScan = ProgramPreferences.getPrefAsArray(ProgramPreferences.Prefs.ControlledPaths);		
	}
	
	private String[] foldersToScan() {
		return foldersToScan;
	}
	
	void newFile(Long length, String pc, String path, String parent, String lastModificated) {
		//create our file abstraction
		JPL_File jPL_file = new JPL_File(length, pc, path, parent, lastModificated);
		//add file to set
		filesHS.add(jPL_file);
		//get files set by length
		Set<JPL_File> sameLengthFilesHS = localLengthsHM.getOrDefault(length, new HashSet<JPL_File>());
		sameLengthFilesHS.add(jPL_file);
		localLengthsHM.putIfAbsent(length, sameLengthFilesHS);
		if (sameLengthFilesHS.size() > 1) {
			sameLengthFilesHS.forEach(file -> checkForDuplicate(file));			
		}		
	}
	
	void checkForDuplicate(JPL_File jpl_file) {
		String checksum = jpl_file.getChecksum();
		Set<JPL_File> sameChecksumSet = localChecksumHM.getOrDefault(checksum, new HashSet<JPL_File>());
		sameChecksumSet.add(jpl_file);
		localChecksumHM.putIfAbsent(checksum, sameChecksumSet);
		if (sameChecksumSet.size() > 1) {
			localDuplicatesHM.putIfAbsent(checksum, sameChecksumSet);
		}
	}
	
	void scanDirectories() {
		new FileWorker(this).scanDirectories(foldersToScan());
		updateTreeModel();
	}

	private void updateTreeModel() {
		for (Map.Entry<String, Set<JPL_File>> pair : localDuplicatesHM.entrySet()) {			
			Set<JPL_File> sameChecksumSet = pair.getValue();
			treeModel.add(sameChecksumSet);
		}
		treeModel.reload();
	}

}
