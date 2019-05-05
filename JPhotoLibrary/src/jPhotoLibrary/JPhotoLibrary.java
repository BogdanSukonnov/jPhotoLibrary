package jPhotoLibrary;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JPhotoLibrary {
	
	static private String[] foldersToScan;
	static final String thisPC = "PC1"; //TODO This PC
	static private Map<Long, Set<JPL_File>> localLengthsHM = new HashMap<>();
	static private Map<String, Set<JPL_File>> localChecksumHM = new HashMap<>();
	static private Map<String, Set<JPL_File>> localDuplicatesHM = new HashMap<>();
	static private Set<JPL_File> filesHS = new HashSet<>();
		
	public static void main(String[] args) {
		setDirectoriesToScan();
		openMainFrame();				
	}
	
	private static void openMainFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
	
	static void setDirectoriesToScan() {
		foldersToScan = ProgramPreferences.getPrefAsArray(ProgramPreferences.Prefs.ControlledPaths);		
	}
	
	static String[] foldersToScan() {
		return foldersToScan;
	}
	
	static void newFile(Long length, String pc, String path, String parent, String lastModificated) {
		//create our file abstraction
		JPL_File jPL_file = new JPL_File(length, pc, path, parent, lastModificated);
		//add file to set
		filesHS.add(jPL_file);
		//get files set by length
		Set<JPL_File> sameLengthFilesHS = localLengthsHM.getOrDefault(length, new HashSet<JPL_File>());
		sameLengthFilesHS.add(jPL_file);
		localLengthsHM.put(length, sameLengthFilesHS);
		if (sameLengthFilesHS.size() > 1) {
			sameLengthFilesHS.forEach(file -> checkForDuplicate(file));			
		}		
	}
	
	static void checkForDuplicate(JPL_File jpl_file) {
		String checksum = jpl_file.getChecksum();
		Set<JPL_File> sameChecksumSet = localChecksumHM.getOrDefault(checksum, new HashSet<JPL_File>());
		sameChecksumSet.add(jpl_file);
		localChecksumHM.put(checksum, sameChecksumSet);
		if (sameChecksumSet.size() > 1) {
			localDuplicatesHM.put(checksum, sameChecksumSet);
		}
	}
	
	static void scanDirectories() {
		new FileWorker().scanDirectories();
		for (Map.Entry<String, Set<JPL_File>> pair : localDuplicatesHM.entrySet()) {			
			Set<JPL_File> sameChecksumSet = pair.getValue();
			for (JPL_File file : sameChecksumSet) {
				System.out.print(" = " + file.getPath());			
			}
			System.out.println(" == " + pair.getKey());
		}		
	}

}
