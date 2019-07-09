package jPhotoLibrary;

public class JPL_File {
	
	private long length;
	private String pc;
	private String path;
	private String parent;
	private String lastModificated;
	private String checksum = "";
	
	public JPL_File(long length, String pc, String path, String parent, String lastModificated) {
		super();
		this.length = length;
		this.pc = pc;
		this.path = path;
		this.parent = parent;
		this.lastModificated = lastModificated;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JPL_File other = (JPL_File) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (pc == null) {
			if (other.pc != null)
				return false;
		} else if (!pc.equals(other.pc))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((pc == null) ? 0 : pc.hashCode());
		return result;
	}

	public long getLength() {
		return length;
	}

	public String getPc() {
		return pc;
	}

	public String getPath() {
		return path;
	}

	public String getParent() {
		return parent;
	}

	public String getLastModificated() {
		return lastModificated;
	}
	
	public String getChecksum() {
		if (checksum.isBlank()) {
			try {
				checksum = FileWorker.hashFile(path);
			} catch (HashGenerationException e) {
				// TODO can't checksum file
				e.printStackTrace();
			}
		}
		return checksum;
	}
	
}
