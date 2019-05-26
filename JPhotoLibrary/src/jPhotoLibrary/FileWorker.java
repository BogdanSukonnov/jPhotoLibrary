package jPhotoLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.h2.util.StringUtils;

class FileWorker {
	
	static String excludedFilesRegexPattern;
	
	public void scanDirectories() {		
		String[] directoriesToScan = JPhotoLibrary.foldersToScan();
		for (String directoryStr : directoriesToScan) {
			//check if the path is correct directory
			File directoryFile = new File(directoryStr);
			if (!directoryFile.exists()) {
				//TODO need to do something when directory from preferences is not valid
				break;
			}
			if (!directoryFile.isDirectory()) {
				//TODO the path is not directory
				break;
			}
			//actually scan thru directory
			try {
				Files.walkFileTree(directoryFile.toPath(), new JPL_FileVisitor());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	class JPL_FileVisitor implements FileVisitor<Path> {
		
		JPL_FileVisitor() {
			//initialize excludedFilesRegexPattern only once 
			if (StringUtils.isNullOrEmpty(excludedFilesRegexPattern)) {
				excludedFilesRegexPattern = "(?i:.*\\.(" + FileTypes.EXCLUDED.forRegex() + "))";
			}
		}
				
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if (file.getFileName().toString().matches(excludedFilesRegexPattern)) {
				//we skip system and auxiliary files				
				return FileVisitResult.CONTINUE;				
			}
			BasicFileAttributes fileAttrs = Files.readAttributes(file, BasicFileAttributes.class);
		    JPhotoLibrary.newFile(fileAttrs.size(), JPhotoLibrary.thisPC, file.toString(), file.getParent().toString(), fileAttrs.lastModifiedTime().toString()); 
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}
			
	}
	
	static public String hashFile(String fileStr) throws HashGenerationException {
		try (FileInputStream inputStream = new FileInputStream(fileStr)) {
	        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	 
	        byte[] bytesBuffer = new byte[1024];
	        int bytesRead = -1;
	 
	        while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
	            digest.update(bytesBuffer, 0, bytesRead);
	        }
	 
	        byte[] hashedBytes = digest.digest();
	 
	        return convertByteArrayToHexString(hashedBytes);
	    } catch (NoSuchAlgorithmException | IOException ex) {
	        throw new HashGenerationException(
	                "Could not generate hash from file", ex);
	    }		   
	}
	
	static public String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
		
}