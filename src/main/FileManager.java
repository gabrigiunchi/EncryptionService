package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileManager {
	
	public static String readFromFile(final String path) throws FileNotFoundException {
		final InputStream in = new FileInputStream(new File(path));
		final StringBuilder buffer = new StringBuilder();
		
		try {
			while(in.available() > 0) {
				buffer.append((char)in.read());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return buffer.toString();
	}
	
	public static void writeToFile(final String path, final String s) throws FileNotFoundException {
		final OutputStream out = new FileOutputStream(new File(path));	
		try {
			out.write(s.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
