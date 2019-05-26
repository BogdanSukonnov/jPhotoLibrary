package jPhotoLibrary;

public enum DB_Constants {
	
	DB_PATH {
		@Override
		public String toString() {
			return "JPhotoLibraryData/photoLibraryDB";
		}
	},
	
	DB_USER {
		@Override
		public String toString() {
			return "mljava";
		}
	},
	
	DB_PASS {
		@Override
		public String toString() {
			return "11FriendsOfOcean!";
		}
	};

}
