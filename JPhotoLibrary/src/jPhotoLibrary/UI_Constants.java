package jPhotoLibrary;

enum UI_Constants {

	SCAN_BTN_TITLE {
		@Override
		public String toString() {
			return "Scan";
		}
	},
	
	PREFS_BTN_TITLE {
		@Override
		public String toString() {
			return "preferences";
		}
	},
	
	MAIN_FRAME_TITLE {
		@Override
		public String toString() {
			return "jPhotoLibrary";
		}
	},
	
	PREFS_NODE_NAME {
		@Override
		public String toString() {
			return "JPhotoLibrarySysPrefs";
		}
	},
	
	PREFS_DIALOG_TITLE {
		@Override
		public String toString() {
			return "Preferences (jPhotoLibrary)";
		}
	};
}
