package jPhotoLibrary;
import java.util.prefs.Preferences;

class ProgramPreferences {
		
	static private Preferences prefsRootNode = Preferences.userRoot().node(Constants.prefsNodeName);
	
	static public enum Prefs {
		ControlledPaths("[]"), MainDB("");
		public final String defaultValue;		
		private Prefs(String defaultValue) {
			this.defaultValue = defaultValue;			
		}
	}
	
	static public String getPref(Prefs pref) {		
		String prefValue = "";		
		try {
			prefValue = prefsRootNode.get(pref.toString(), pref.defaultValue);
		}
		catch (Exception e) {
			System.out.print(e.toString());
		}				
		return prefValue;				
	}
	
	static public Boolean putPref(Prefs pref, String prefValue) {		
		Boolean succesfully = true;		
		try {
			prefsRootNode.put(pref.toString(), prefValue);
		}
		catch (Exception e) {
			System.out.print(e.toString());
			succesfully = false;
		} 		
		return succesfully;		
	}
	
	static public String[] getPrefAsArray(Prefs pref) {		
		String[] prefValueArr = stringArrayFromString(getPref(pref));
		return prefValueArr;				
	}	
	
	static private String[] stringArrayFromString(final String inputString) {
		String workStr = inputString.replace("[, ", "");
		workStr = workStr.replace("[", "");
		workStr = workStr.replace("]", "");
		String[] arr = workStr.isBlank() ? new String[0] :workStr.split(", ");
		return arr;
	}
	
}
