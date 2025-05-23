package io.github.ullas.k.prabhakar.openai.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;

public class RavenUtil {

	private static final long MAX_FILE_SIZE = 10L * 1024 * 1024; // 10 MB

	/**
	 * Reads the provided image file and returns its Base64-encoded string. Rejects
	 * files larger than 10 MB.
	 *
	 * @param imageFile the image file to encode
	 * @return Base64-encoded representation of the image
	 * @throws IOException              if reading the file fails
	 * @throws IllegalArgumentException if the file size exceeds 10 MB
	 */
	public static String encodeImageToBase64(File imageFile) throws IOException {
		if (imageFile.length() > MAX_FILE_SIZE) {
			throw new IllegalArgumentException("File size exceeds maximum allowed of 10 MB: " + imageFile.length());
		}
		byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	/**
	 * Encodes the provided image bytes to a Base64-encoded string. Rejects byte
	 * arrays larger than 10 MB.
	 *
	 * @param imageBytes byte array of the image data
	 * @return Base64-encoded representation of the image
	 * @throws IllegalArgumentException if the byte array exceeds 10 MB
	 */
	public static String encodeImageToBase64(byte[] imageBytes) {
		if (imageBytes.length > MAX_FILE_SIZE) {
			throw new IllegalArgumentException(
					"Image byte array exceeds maximum allowed size of 10 MB: " + imageBytes.length);
		}
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	/**
	 * Checks if the provided string is a valid file path to an existing file.
	 *
	 * @param path string representing the file path
	 * @return true if the file exists and is a file, false otherwise
	 */
	public static boolean isValidFilePath(String path) {
		if (path == null || path.isEmpty()) {
			return false;
		}
		File file = new File(path);
		return file.exists() && file.isFile();
	}

	/**
	 * Checks if the provided string is valid Base64-encoded data.
	 *
	 * @param base64 string to validate
	 * @return true if the string is valid Base64, false otherwise
	 */
	public static boolean isValidBase64(String base64) {
		if (base64 == null || base64.isEmpty()) {
			return false;
		}
		try {
			Base64.getDecoder().decode(base64);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public static String getMimeTypeByExtension(String fileName) {
		fileName = (fileName == null) ? "" : fileName;
		if (fileName.endsWith(".png"))
			return "image/png";
		if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
			return "image/jpeg";
		return "image/jpeg";
	}

	/**
	 * Reads the entire file into a byte array.
	 *
	 * @param file the file to read
	 * @return a byte[] containing the file’s contents
	 * @throws IOException if an I/O error occurs
	 */
	public static byte[] fileToByteArray(File file) throws IOException {
		return Files.readAllBytes(file.toPath());
	}

	/**
	 * Writes an InputStream to a file.
	 *
	 * @param inputStream The input stream to write.
	 * @param targetFile  The file to write to.
	 * @throws IOException if an I/O error occurs.
	 */
	public static void writeToFile(InputStream inputStream, File targetFile) throws IOException {
		try (FileOutputStream outStream = new FileOutputStream(targetFile)) {
			byte[] buffer = new byte[8192]; // 8 KB buffer
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
		} finally {
			inputStream.close();
		}
	}
	
	public static void writeToFile(byte[] data, File file) throws IOException {
	    try (FileOutputStream fos = new FileOutputStream(file)) {
	        fos.write(data);
	        fos.flush(); // optional, but good practice
	    }
	}
	
	public static byte[] toByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    byte[] data = new byte[4096]; // 4KB buffer
	    int bytesRead;
	    while ((bytesRead = input.read(data)) != -1) {
	        buffer.write(data, 0, bytesRead);
	    }
	    return buffer.toByteArray();
	}
}
